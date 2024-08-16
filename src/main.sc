require: slotfilling/slotFilling.sc
  module = sys.zb-common

require: common.js
    module = sys.zb-common
theme: /

    state: Правила
        q!: $regex</start>
        q!: Давай поиграем
        a: Игра больше-меньше. Загадаю число от 0 до 100, ты будешь отгадывать. Начнём?
        go!: Согласен?

        state: Согласен?

            state: Да
                intent: /согласие
                go!: /Game
            state: Нет
                q: Нет
                a: Ну и ладно! Если передумаешь — скажи "давай поиграем"

    state: Game
        script:
            function getRandomIntInclusive(min, max) {
                var minCeiled = Math.ceil(min);
                var maxFloored = Math.floor(max);
                return Math.floor(Math.random() * (maxFloored - minCeiled + 1) + minCeiled); // The maximum is inclusive and the minimum is inclusive
            }
            $session.number = getRandomIntInclusive(999, 10000);
            $reactions.transition("/Проверка");
        
            
    state: Проверка
        intent: /число
        a: Я загадал число {{ $session.number}}.
        script:
            # сохраняем введенное пользователем число
            var num = String($parseTree._Number);
    
            if (num === String($session.number)) {  // Используем строгое сравнение === для строк
                    $reactions.answer("Ты выиграл! Хочешь еще раз?");
            } 
            
            function calculateBullsAndCows($session.number, guess) {
                let bulls = 0;
                let cows = 0;
                const secretDigits = $session.number.split('');
                const guessDigits = guess.split('');
            
                // Подсчитываем быков
                for (let i = 0; i < 4; i++) {
                    if (guessDigits[i] === secretDigits[i]) {
                        bulls++;
                        secretDigits[i] = null; // Убираем правильно угаданный элемент
                        guessDigits[i] = undefined; // Убираем правильно угаданный элемент
                    }
                }
            
                // Подсчитываем коров
                for (let i = 0; i < 4; i++) {
                    if (guessDigits[i] !== undefined) {
                        const index = secretDigits.indexOf(guessDigits[i]);
                        if (index !== -1) {
                            cows++;
                            secretDigits[index] = null; // Убираем найденный элемент
                        }
                    }
                }
            
                return { bulls, cows };
            }
            
            // Функция для обработки попытки пользователя
            function handleGuess($session.number, guess) {
                if (guess.length !== 4 || new Set(guess).size !== 4 || !/^\d+$/.test(guess)) {
                    return "Введите корректное 4-значное число с неповторяющимися цифрами.";
                }
            
                const { bulls, cows } = calculateBullsAndCows($session.number, guess);
                if (bulls === 4) {
                    return "Поздравляю! Вы угадали число!";
                } else {
                    return `Быки: ${bulls}, Коровы: ${cows}`;
                }
            }
            console.log(handleGuess($session.number, userGuess));


        
    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Я не понял.
            a: Что вы имеете в виду?
            a: Ничего не пойму
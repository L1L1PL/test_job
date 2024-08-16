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
            var num = ($parseTree._Number);
    
            if (num === String($session.number)) {  // Используем строгое сравнение === для строк
                    $reactions.answer("Ты выиграл! Хочешь еще раз?");
            } else {
                var bulls = 0;
                var cows = 0;
                var num = num.toString();
                var guess_number = $session.number.toString();
                let bullDigits = new Array();
                let cowDigits = new Array(); 
                let bullDigits = []; // Массив для цифр, угаданных как быки
                let cowDigits = [];  // Массив для цифр, угаданных как коровы
                
                // Считаем быков и коров
                for (var i = 0; i < 4; i++) {
                    if (num.charAt(i) === guess_number.charAt(i)) {
                        bulls++;
                        bullDigits.push(num.charAt(i)); // Добавляем цифру быка
                    } else if (guess_number.indexOf(num.charAt(i)) !== -1) {
                        cows++;
                        cowDigits.push(num.charAt(i)); // Добавляем цифру коровы
                    }
                }
                var react_bull;
                if (bulls === 0) {
                    var react_bull = 'быков';
                } else if (bulls === 1) {
                    var react_bull = 'бык';
                } else {
                    var react_bull = 'быка';
                }
                
                var react_cows;
                if (cows=== 0) {
                    var react_cows = 'коров';
                } else if (bulls === 1) {
                    var react_cows = 'корова';
                } else {
                    var react_cows = 'коровы';
                }
            
            
            $reactions.answer("Быки: " + bulls +" "+ react_bull +"(" + items_bulls +")" + ", Коровы: " + cows + " " + react_cows);
            }
        
    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Я не понял.
            a: Что вы имеете в виду?
            a: Ничего не пойму
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
            return Math.floor(Math.random() * (maxFloored - minCeiled + 1) + minCeiled); // Включаем и минимальное, и максимальное значение
        }
        $session.number = getRandomIntInclusive(1000, 9999);  // Генерируем четырехзначное число
        $reactions.transition("/Проверка");

    state: Проверка
        intent: /число
        a: Я загадал число {{ $session.number}}.
        script:
            # Сохраняем введенное пользователем число
            var num = String($parseTree._Number);  // Приводим к строке для корректной работы charAt()
    
            // Проверяем, угадал ли пользователь загаданное число, и выводим соответствующую реакцию
            if (num === String($session.number)) {  // Используем строгое сравнение === для строк
                $reactions.answer("Ты выиграл! Хочешь еще раз?");
            } else {
                var bulls = 0;
                var cows = 0;
                for (var i = 0; i < 4; i++) {
                    if (num.charAt(i) === String($session.number).charAt(i)) {
                        bulls++;
                    } else if (String($session.number).includes(num.charAt(i))) {
                        cows++;
                    }
                }
                // Выводим количество быков и коров
                $reactions.answer("Быки: " + bulls + ", Коровы: " + cows);
            }
    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Я не понял.
            a: Что вы имеете в виду?
            a: Ничего не пойму
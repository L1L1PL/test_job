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
        # сгенерируем случайное число и перейдем в стейт /Проверка
        script:
            $session.number = $jsapi.random(100) + 1;
            $reactions.answer("Загадано {{$session.number}}");
            $reactions.transition("/Проверка");

    state: Проверка
        intent: /число
        script:
            # сохраняем введенное пользователем число
            var num = $parseTree._Number;

            # проверяем угадал ли пользователь загаданное число и выводим соответствующую реакцию
            if (num == $session.number) {
                $reactions.answer("Ты выиграл! Хочешь еще раз?");
            }
            else
                if (num < $session.number)
                    $reactions.answer(selectRandomArg(["Мое число больше!", "Бери выше", "Попробуй число больше"]));
                else $reactions.answer(selectRandomArg(["Мое число меньше!", "Подсказка: число меньше", "Дам тебе еще одну попытку! Мое число меньше."]));

    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Я не понял.
            a: Что вы имеете в виду?
            a: Ничего не пойму
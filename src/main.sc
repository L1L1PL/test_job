require: slotfilling/slotFilling.sc
  module = sys.zb-common

require: common.js
    module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Привет! Хочешь сыграть в игру "Быки и корова"?
        go!: /Answer

    state: Hello
        q!: привет
        a: Привет-привет. Хочешь сыграть в игру "Быки и корова"?
        go!: /Answer
    
    state: GameStart
        q!: игра
        a: Начнем игру?
        state: Да
            q: Да
            go!: /Game
        state: Нет
            q: Да
            a: Ну и ладно! Если передумаешь — скажи "давай поиграем"
        

    state: Answer

        state: Да
            q: Да
            a: Отлично
            go!: /Rules

        state: Нет
            q: Нет
            a: Ну и ладно! Если передумаешь — скажи "давай поиграем"
    state: Rules
        q!: Правила
        a: Ты уже знаком с правилами?
        state: Да
            q: Да
            script:
                $session.number = 0;
            go!: /GameStart

        state: Нет
            q: Нет
            a: Ничего, я расскажу.
            a: Игра рассчитана на двух игроков (пользователь и чатбот). Чатбот задумывает тайное 4-значное число с неповторяющимися цифрами. Пользователь делает первую попытку отгадать число. Попытка — это 4-значное число с неповторяющимися цифрами, сообщаемое чатботу. Чатбот сообщает в ответ, сколько цифр угадано без совпадения с их позициями в тайном числе (то есть количество коров) и сколько угадано вплоть до позиции в тайном числе (то есть количество быков)
            a: Теперь ты знаком с правилами. Начинаем?
            
            state: Да
                q: Да
                a: Отлично
                go!: /Game

            state: Нет
                go!: /Answer/Нет
                
    state: Game
        script:
            function getRandomIntInclusive(min, max) {
                var minCeiled = Math.ceil(min);
                var maxFloored = Math.floor(max);
                return Math.floor(Math.random() * (maxFloored - minCeiled + 1) + minCeiled); // The maximum is inclusive and the minimum is inclusive
            }
            $session.number = getRandomIntInclusive(999, 10000);
            $reactions.transition("/Проверка");
            
        a: Я загадал число {{ $session.number}}.

        
        state: Проверка
            q: /Число
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
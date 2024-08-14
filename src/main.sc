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
            function getRandomIntInclusive() {
                var minCeiled = Math.ceil(1000);
                var maxFloored = Math.floor(9999);
                var numb = Math.floor(Math.random() * (maxFloored - minCeiled + 1) + minCeiled); // The maximum is inclusive and the minimum is inclusive
                $session.number = var numb
            }
            
        a: Я загадывал число {{$session.number}}.
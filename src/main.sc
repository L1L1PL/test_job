require: slotfilling/slotFilling.sc
  module = sys.zb-common

require: common.js
    module = sys.zb-common
require: scripts/functions.js

theme: /

    state: Start
        q!: $regex</start>
        q!: привет
        a: Привет! Давай поиграем в игру "Быки и коровы"?
        
        state: Согласие
            q: * (*хорошо*|*да*|*давай*|*ок*) *
            a: Отлично! Ты уже знаком с правилами?
        
        state: Несогласие
            q: * (*нет*|*не хочу*|*не надо*) *
            q: нет || fromState = "/Rules"
            a: Жаль. Если передумаешь — скажи "давай поиграем" :-)
    
    state: Rules
        q: * (нет) * || fromState = "/Start/Согласие"
        a: Я загадаю четырехзначное число, в котором все цифры разные. Твоя задача - угадать мое число. В процессе игры я буду давать тебе подсказки. Например, если я загадал число «1234», а ты ввел «2134», то я напишу: "Результат: две «коровы» (две цифры: «1», «2» — угаданы на неверных позициях) и два «быка» (две цифры: «3», «4» — угаданы вплоть до позиции)". Удачи!)
        a: Начнем?
    
    state: RepeatRules
        q!: * (*правила*|*помощь*|*как играть*|*что делать*) *
        q!: $regex</rules>
        a: Я загадаю четырехзначное число, в котором все цифры разные. Твоя задача - угадать мое число. В процессе игры я буду давать тебе подсказки. Например, если я загадал число «1234», а ты ввел «2134», то я напишу: "Результат: две «коровы» (две цифры: «1», «2» — угаданы на неверных позициях) и два «быка» (две цифры: «3», «4» — угаданы вплоть до позиции)". Удачи!)

    state: Game_start
        q!: * (*заново*|*еще*|*еще раз*|*новая игра*|*игра*|*играть*) *
        q: да || fromState = "/Start/Согласие"
        q: да || fromState = "/Rules"
        a: Я загадал число. Попробуй угадать
        script:
            $session.number = getRandomIntInclusive();
            
    state: Проверка
        intent: /число
        script:
            if (($parseTree._Number).toString().length != 4) {
                $reactions.answer("Пожалуйста, напиши четырехзначное число.");
            } else if (hasDuplicateDigits($parseTree._Number)) {  
                 $reactions.answer("Цифры не могут повторяться. Попробуй снова.");
            } else {checkNumber($parseTree._Number, $session.number);}
            
        state: Agree
            q: да
            go!: /Game_start
        state: Disagree
            q: нет
            go!: /Start/Несогласие
        
    state: Ответ
        q!: Ответ
        q!: $regex</answer>
        a: Я загадал число {{ $session.number}}.

    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Я не понял.
            a: Что вы имеете в виду?
            a: Ничего не пойму.
            a: Я не понимаю.
            a: Ой, я таких слов не знаю: {{$request.query}}
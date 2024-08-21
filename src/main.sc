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
            q: нет
            q: нет || fromState = "/Rules"
            a: Жаль. Если передумаешь — скажи "давай поиграем" :-)
    
    state: Rules
        q!: правила
        q: * (нет) * || fromState = "/Start/Согласие"
        a: Расскажу правила
        a: Начнем игру?

    state: Game
        q!: Заново 
        q: да || fromState = "/Start/Согласие"
        q: да || fromState = "/Rules"
        a: Я загадал число. Попробуй угадать
        script:
            $session.number = getRandomIntInclusive();
        go: /Проверка
            
    state: Проверка
        intent: /число
        script:
            if (($parseTree._Number).toString().length != 4) {
                $reactions.answer("Пожалуйста, напиши четырехзначное число.");
            }else{checkNumber($parseTree._Number, $session.number);}
    

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
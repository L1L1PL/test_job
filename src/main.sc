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
            go!: /Game_start
        state: Несогласие
            q: нет
            a: Жаль. Если передумаешь — скажи "давай поиграем" :-)

  
    state: Game_start
        a: Я загадал число. Попробуй угадать
        script:
                $session.number = getRandomIntInclusive();
                $reactions.transition("/Проверка");
            
    state: Проверка
        intent: /число
        script:
            # сохраняем введенное пользователем число
            var num = $parseTree._Number;
        
            # проверяем угадал ли пользователь загаданное число и выводим соответствующую реакцию
            if (num == $session.number) {
                    $reactions.answer("Ты выиграл! Хочешь еще раз?");
            }else{
                check(num)}
                
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
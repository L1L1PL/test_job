require: slotfilling/slotFilling.sc
  module = sys.zb-common

require: common.js
    module = sys.zb-common
theme: /

    state: Правила
        q!: $regex</start>
        q!: Давай поиграем
        a: Привет! Давай поиграем в игру "Быки и коровы"?
        
        state: Согласие
            q: да
            a: Отлично! Ты уже знаком с правилами?
            go!: /Rules
        state: Несогласие
            q: нет
            a: Жаль. Если передумаешь — скажи "давай поиграем" :-)
    
    state: Rules
        q!: Правила

        state: Да
                q: да
                go!: /Game_start
    
    state: Game_start
        a: Я загадал число. Попробуй угадать
        go: Game
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
                var bulls = 0;
                var cows = 0;
                var num = num.toString();
                var guess_number = $session.number.toString();
                var bull_list = [];
                var cow_list = [];
                // Считаем быков и коров
                for (var i = 0; i < 4; i++) {
                    if (num.charAt(i) === guess_number.charAt(i)) {
                        bulls++;
                        bull_list.push(num.charAt(i));
    
                    } else if (guess_number.indexOf(num.charAt(i)) !== -1) {
                        cows++;
                        cow_list.push(num.charAt(i));
                            
                    }
                }
                var word;
                    if (i === 1){
                      var word = 'одна'
                  } else if (i === 2) {
                      var word = 'две'
                  } else if (i === 3) {
                      var word = 'три'
                  } else if (i === 4) {
                      var word = 'четыре' 
                  }
                if (bulls === 4) {
                     $reactions.answer("Ты выиграл! Хочешь еще раз?");
                }else {
                    var react_bull;
                    if (bulls === 0) {
                        var react_bull = bulls +' быков.';
                    } else if (bulls === 1) {
                        var react_bull = bulls + ' бык' + "(одна цифра: «"+bull_list+"» - угадана вплоть до позиции).";
                    } else {
                        var react_bull = bulls + ' быка' + "(" + word + " цифры: «"+bull_list.join('», «')+"» - угаданы вплоть до позиции).";
                    }
                        
                    var react_cows;
                    if (cows=== 0) {
                        var react_cows = cows + ' коров и ';
                    } else if (cows === 1) {
                        var react_cows = cows + ' корова' + "(одна цифра: «"+cow_list+"» - угадана на неверной позици) и ";
                    } else {
                        var react_cows = cows + ' коровы' + "(" + word + " цифры: «"+cow_list.join('», «')+"» - угаданы на неверных позициях) и ";
                    }
                        
                    $reactions.answer("Результат: " + react_cows + react_bull);
                }
        
    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Я не понял.
            a: Что вы имеете в виду?
            a: Ничего не пойму
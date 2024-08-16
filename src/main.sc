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
                function def_numb(i, list){
                    var word;
                    if (i === 2) {
                          var word = "две цифры: «"list[0]+"» и «"list[1]+"» - угаданы "
                      } else if (i === 3) {
                          var word = "три цифры: «"list[0]+"», «"list[1]+"» и «"list[2]+"» - угаданы "
                      } else if (i === 4) {
                          var word = "четыре цифры: «"list[0]+"», «"list[1]+"», «"list[2]+"» и «"list[3]+"» - угаданы "
                      }
                    return(word)
                }
                var react_bull;
                if (bulls === 0) {
                    var react_bull = bulls +' быков.';
                } 
                    var react_bull = bulls + ' бык' + "(одна цифра: «"+bull_list+"» - угадана вплоть до позиции).";;
                } else {
                    var react_bull = ' быка';
                }
                
                var react_cows;
                if (cows=== 0) {
                    var react_cows = cows + ' коров и ';
                } else if (cows === 1) {
                    var react_cows = cows + ' корова' + "(одна цифра: «"+cow_list+"» - угадана на неверной позици) и ";
                } else {
                    var react_cows = console.log(def_numb(cows, cow_list)) + "на неверных позициях) и "

                }
                
            
            
            $reactions.answer("Результат: " + react_cows + react_bull);
            }
        
    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Я не понял.
            a: Что вы имеете в виду?
            a: Ничего не пойму
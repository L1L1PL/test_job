require: slotfilling/slotFilling.sc
  module = sys.zb-common

require: common.js
    module = sys.zb-common
theme: /

    state: Правила
        q!: $regex</start>
        a: Привет! Давай поиграем в игру "Быки и коровы"?
        
        state: Согласие
            q: да
            q: давай
            a: Отлично! Ты уже знаком с правилами?
            go!: /Rules
        state: Несогласие
            q: нет
            a: Жаль. Если передумаешь — скажи "давай поиграем" :-)
    
    state: Rules
        
        state: Да
            q: да
            go!: /Game_start
        state: Нет
            q: нет
            q!: $regex</rules>
            a: Правила игры:
            a: Игра рассчитана на двух игроков (пользователь и чатбот). Чатбот задумывает тайное 4-значное число с неповторяющимися цифрами. Пользователь делает первую попытку отгадать число. Попытка — это 4-значное число с неповторяющимися цифрами, сообщаемое чатботу. Чатбот сообщает в ответ, сколько цифр угадано без совпадения с их позициями в тайном числе (то есть количество коров) и сколько угадано вплоть до позиции в тайном числе (то есть количество быков). Например: Задумано чатботом тайное число «3219». Попытка: «2310». Результат: две «коровы» (две цифры: «2» и «3» — угаданы на неверных позициях) и один «бык» (одна цифра «1» угадана вплоть до позиции).
            a: Теперь можем начинать?
            state: да
                q: да
                go!: /Game_start
            state: нет
                q: нет
                a: Жаль. Если передумаешь — скажи "давай поиграем" :-)
    state: Again
        q: да
        intent!: /new_game
        a: Начнем игру!
        go!: /Game_start
    state: Help
        q!: Правила
        q!: Помощь
        a: Давай напомню тебе правила.
        a:Игра рассчитана на двух игроков (пользователь и чатбот). Чатбот задумывает тайное 4-значное число с неповторяющимися цифрами. Пользователь делает первую попытку отгадать число. Попытка — это 4-значное число с неповторяющимися цифрами, сообщаемое чатботу. Чатбот сообщает в ответ, сколько цифр угадано без совпадения с их позициями в тайном числе (то есть количество коров) и сколько угадано вплоть до позиции в тайном числе (то есть количество быков). Например: Задумано чатботом тайное число «3219». Попытка: «2310». Результат: две «коровы» (две цифры: «2» и «3» — угаданы на неверных позициях) и один «бык» (одна цифра «1» угадана вплоть до позиции).
        
    
    state: Game_start
        a: Я загадал число. Попробуй угадать
        go!: /Игра

    state: Игра
            script:
                function getRandomIntInclusive() {
                    var digits = '0123456789';
                    var usedDigits = [];
                    var number = '';
                
                    while (number.length < 4) {
                        var randomIndex = Math.floor(Math.random() * digits.length);
                        var digit = digits[randomIndex];
                
                        // Проверяем, чтобы цифра не повторялась
                        if (usedDigits.indexOf(digit) === -1) {
                            usedDigits.push(digit);
                            number += digit;
                        }
                    }
                
                    return number;
                
                }
                $session.number = getRandomIntInclusive();
                //$reactions.transition("/Проверка");
            go: /Проверка
            
                
    state: Проверка
            intent: /число
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
                var message = false;
                for (var i = 0; i < 4; i++) {
                    if (num[i] === num[i - 1]) {
                        message = true;  // Нет необходимости повторно объявлять переменную
                    }
                }
                
                // Выражение 'else if' не имеет смысла здесь
                if (message === true) {
                    $reactions.answer("Цифры должны быть разными");
                }
                else if (num.length != 4) {
                    $reactions.answer("Пожалуйста, напиши четырехзначное число");
                }else{
                    for (var i = 0; i < 4; i++) {
                        if (num.charAt(i) === guess_number.charAt(i)) {
                            bulls++;
                            bull_list.push(num.charAt(i));
        
                        } else if (guess_number.indexOf(num.charAt(i)) !== -1) {
                            cows++;
                            cow_list.push(num.charAt(i));
                        }    
                    }
                
                    if (bulls === 4) {
                         $reactions.answer("Ты выиграл! Хочешь еще раз?");
                    }
                    
                    else {
                        var word;
                        if (bulls === 2) {
                          var word = 'две'
                        } else if (bulls === 3) {
                          var word = 'три'
                        } else if (bulls === 4) {
                          var word = 'четыре' 
                        }
                        var react_bull;
                        if (bulls === 0) {
                            var react_bull = bulls +' быков.';
                        } else if (bulls === 1) {
                            var react_bull = bulls + ' бык ' + "(одна цифра: «"+bull_list+"» - угадана вплоть до позиции).";
                        } else {
                            var react_bull = bulls + ' быка ' + "(" + word + " цифры: «"+bull_list.join('», «')+"» - угаданы вплоть до позиции).";
                        }
                            
                        var react_cows;
                        var word_c;
                        if (cows === 2) {
                          var word_c = 'две'
                        } else if (cows === 3) {
                          var word_c = 'три'
                        } else if (cows === 4) {
                          var word_c = 'четыре' 
                        }
                        if (cows=== 0) {
                            var react_cows = cows + ' коров и ';
                        } else if (cows === 1) {
                            var react_cows = cows + ' корова ' + "(одна цифра: «"+cow_list+"» - угадана на неверной позици) и ";
                        } else {
                            var react_cows = cows + ' коровы ' + "(" + word_c + " цифры: «"+cow_list.join('», «')+"» - угаданы на неверных позициях) и ";
                        }
                            
                        $reactions.answer("Результат: " + react_cows + react_bull);
                    }
                }
    
    state: Ответ
        q!: Ответ
        q!: $regex</answer>
        a: Я загадал число {{ $session.number}}.

    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Я не понял.
            a: Что вы имеете в виду?
            a: Ничего не пойму
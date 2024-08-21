function getRandomIntInclusive() {
    var digits = '0123456789'.split('');
    var res = [];
    for (var i = 0; i < 4; i++) {
        var randomIndex;
        if (i === 0) {
            // Для первой цифры исключаем 0
            var firstDigits = digits.slice(1); 
            randomIndex = Math.floor(Math.random() * firstDigits.length);
            res.push(firstDigits[randomIndex]);
            digits.splice(digits.indexOf(firstDigits[randomIndex]), 1); 
        } else {
            randomIndex = Math.floor(Math.random() * digits.length);
            res.push(digits[randomIndex]);
            digits.splice(randomIndex, 1); 
        }
    }
    return res.join('');
}

function hasDuplicateDigits(number){
    var digits = number.toString().split(''); 
    var seen = {}; 
    for (var i = 0; i < digits.length; i++) {
        var digit = digits[i];
        if (seen[digit]) {
            return true; 
        }
        seen[digit] = true;
    }
    return false; 
}

function checkNumber(num, guess) {
    if (hasDuplicateDigits(num)) {
        $reactions.answer("Цифры не могут повторяться. Попробуй снова.");
    }else if (num === guess) {
        $reactions.answer("Ты выиграл! Хочешь еще раз?");
    }else {
        var bulls = 0;
        var cows = 0;
        var bull_list = [];
        var cow_list = [];
        var num = num.toString()
        for (var i = 0; i < 4; i++) {
            if (num.charAt(i) === guess.toString().charAt(i)) {
                bulls++;
                bull_list.push(num.charAt(i));
        
            } else if (guess.toString().indexOf(num.charAt(i)) !== -1) {
                cows++;
                cow_list.push(num.charAt(i));
                }    
            }
    } return answer(bulls, cows, bull_list, cow_list)
}   

function ReturnWord(numb){
    if (numb === 2) {
      var word = 'две'
    } else if (numb === 3) {
      var word = 'три'
    } else if (numb === 4) {
      var word = 'четыре' 
    } return(word)
}

function answer(bulls, cows, bull_list, cow_list){
    var react_bull;
    if (bulls === 0) {
        var react_bull = bulls +' быков.';
    } else if (bulls === 1) {
        var react_bull = bulls + ' бык ' + "(одна цифра: «"+bull_list+"» - угадана вплоть до позиции).";
    } else {
        var word = ReturnWord(bulls)
        var react_bull = bulls + ' быка ' + "(" + word + " цифры: «"+bull_list.join('», «')+"» - угаданы вплоть до позиции).";
    }
    
    var react_cows;
    if (cows=== 0) {
        var react_cows = cows + ' коров и ';
    } else if (cows === 1) {
        var react_cows = cows + ' корова ' + "(одна цифра: «"+cow_list+"» - угадана на неверной позици) и ";
    } else {
        var word = ReturnWord(cows)
        var react_cows = cows + ' коровы ' + "(" + word + " цифры: «"+cow_list.join('», «')+"» - угаданы на неверных позициях) и ";
    }
    return($reactions.answer("Результат: " + react_cows + react_bull));
}
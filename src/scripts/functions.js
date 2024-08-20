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

function hasDuplicateDigits(number) {
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
    }else if (num == guess) {
        $reactions.answer("Ты выиграл! Хочешь еще раз?");
    } else {
        $reactions.answer("Попытки");
    }
}
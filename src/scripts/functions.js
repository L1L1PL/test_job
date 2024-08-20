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

function check(num){
    var num = '5';
    return($reactions.answer("Попробуй еще"));
}
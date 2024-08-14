require: patterns.sc

require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        random: 
            a: Привет, я скучал 
            a: Здравствуй!
            a: Приветик! Рад тебя видеть :)
            a: Ой, ты снова пришел :)

    state: Hello
        intent!: /привет
        a: Привет привет
        
    state: MarcoPolo 
        q!: Марко 
        a: Поло
        go!: /MyTest2
        
    state: MyTest2
        random:
            a: Мои отлично!
            a: У меня все хорошо :) 
            a: Лучше всех. Хорошо, что никто не завидует.
            a: Как в сказке 
            a: Расту, цвету, старею…Всё как обычно
            a: После того, как ты спросил, намного лучше.

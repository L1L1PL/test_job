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
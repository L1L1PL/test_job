require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Привет! Хочешь сыграть в игру "Быки и корова"?

    state: Hello
        q!: привет
        a: Привет-привет.Хочешь сыграть в игру "Быки и корова"?
        
        state: Сonfirmation 
            q: Да 
            a: Отлично! Ты знаком с правилами?


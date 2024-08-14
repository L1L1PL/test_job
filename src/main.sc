require: patterns.sc

require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        script:
        a: Привет! Хочешь сыграть в игру "Быки и корова"?
    state: Confirmation
        q: Да
        a: Отлично :-) 
        go!: /Rules
    state: Rejection
        q: Нет
        random! a: Мне жаль | Если захочешь, буду ждать | 
    
    state: Rules
        q!Правила
        a:Ты знаком с правилами?

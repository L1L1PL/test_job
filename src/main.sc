require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Привет! Хочешь сыграть в игру "Быки и корова"?

    state: Hello
        q!: привет
        a: Привет-привет.Хочешь сыграть в игру "Быки и корова"?
        go!: /Согласен?

    state: Согласен?

        state: Да
            q: Да
            a: Отлично

        state: Нет
            q: Нет
            a: Ну и ладно! Если передумаешь — скажи "давай поиграем"

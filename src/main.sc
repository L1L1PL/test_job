require: slotfilling/slotFilling.sc
  module = sys.zb-common

require: common.js
    module = sys.zb-common

theme: /

   state: Правила
    q!: $regex</start>
    intent!: /Давай поиграем
    a: Игра больше-меньше. Загадаю число от 0 до 100, ты будешь отгадывать. Начнём?
    go!: /Правила/Согласен?

    state: Согласен?

        state: Да
            intent: /Согласие
            go!: /Игра

        state: Нет
            intent: /Несогласие
            a: Ну и ладно! Если передумаешь — скажи "давай поиграем"
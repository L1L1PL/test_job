require: slotfilling/slotFilling.sc
  module = sys.zb-common

require: common.js
    module = sys.zb-common

theme: /

    state:  Start
        q!: $regex</start>
        a: Привет! Давай поиграем в "Быки и коровы"?
        if: "Да"
            a: Ты знаком с правилами?
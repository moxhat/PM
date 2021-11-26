package com.madcrew.pravamobil.models.responsemodels

data class ChekPaymentStatusResponse(
    var payStatus: String? = null,
    var amount: String? = null,
    var status: String? = null
)

//empty - дефолтный статус
//pending - платеж создан и ожидает действия пользователя
//waiting_for_capture - платеж оплачен, деньги авторизованы и ожидают списание
//succeeded - платеж успешно оплачен
//canceled - платеж отменен (отмена/истекло время/отклонен)
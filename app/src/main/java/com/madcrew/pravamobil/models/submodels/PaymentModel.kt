package com.madcrew.pravamobil.models.submodels

data class PaymentModel(
    var payment: String?, //Способ оплаты (0 - полная оплата / 1 - 1 месяц / 2 - 2 месяца и тд. максимум 5 месяцев
    var installment: String?,
    var price: String?

)

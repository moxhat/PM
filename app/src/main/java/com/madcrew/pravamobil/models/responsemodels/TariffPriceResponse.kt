package com.madcrew.pravamobil.models.responsemodels

data class TariffPriceResponse(
    var amount: String? = null,
    var fullPay: String? = null,
    var credit: String? = null,
    var status: String? = null
)

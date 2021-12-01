package com.madcrew.pravamobil.models.responsemodels

data class PayInfoResponse(
    var amount: String? = null,
    var pay: Int? = null,
    var debt: Int? = null,
    var sPay: Int? = null,
    var nextDate: String? = null,
    var nextAmount: Int? = null,
    var expired: Boolean? = null,
    var status: String? = null
)
package com.madcrew.pravamobil.models.requestmodels

data class AdditionalPaymentRequest(
    var token: String,
    var school_id: Int,
    var client_id: Int,
    var price_id: Int,
    var count: Int
)

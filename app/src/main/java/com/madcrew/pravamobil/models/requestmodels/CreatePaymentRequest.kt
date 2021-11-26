package com.madcrew.pravamobil.models.requestmodels

data class CreatePaymentRequest (
    var token: String,
    var school_id: Int,
    var client_id: Int,
    var first: Boolean
        )
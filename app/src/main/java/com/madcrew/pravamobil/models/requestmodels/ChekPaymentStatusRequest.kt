package com.madcrew.pravamobil.models.requestmodels

data class ChekPaymentStatusRequest(
    val token: String,
    val school_id: Int,
    val client_id: Int,
    val pay_id: Boolean,
    val first: Boolean
)

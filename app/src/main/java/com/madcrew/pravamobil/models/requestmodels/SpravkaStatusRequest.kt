package com.madcrew.pravamobil.models.requestmodels

data class SpravkaStatusRequest(
    var token: String,
    var school_id: String,
    var client_id: String
)

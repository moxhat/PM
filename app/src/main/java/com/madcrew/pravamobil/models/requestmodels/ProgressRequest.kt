package com.madcrew.pravamobil.models.requestmodels

data class ProgressRequest(
    var token: String,
    var school_id: String,
    var client_id: String,
    var progress: String
)

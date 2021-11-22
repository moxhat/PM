package com.madcrew.pravamobil.models.responsemodels

data class StatusWithErrorResponse(
    var status: String,
    var error: String? = null
)

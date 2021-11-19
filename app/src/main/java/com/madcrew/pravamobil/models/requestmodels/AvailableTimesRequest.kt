package com.madcrew.pravamobil.models.requestmodels

data class AvailableTimesRequest(
    val token: String,
    val school_id: String,
    val instructor_id: String,
    val date: String
)

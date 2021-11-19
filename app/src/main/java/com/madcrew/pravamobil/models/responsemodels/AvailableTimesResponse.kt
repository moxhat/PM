package com.madcrew.pravamobil.models.responsemodels

data class AvailableTimesResponse(
    var times: List<AvailableTimes>? = null,
    var status: String
)

data class AvailableTimes(
    var id: String,
    var title: String
)

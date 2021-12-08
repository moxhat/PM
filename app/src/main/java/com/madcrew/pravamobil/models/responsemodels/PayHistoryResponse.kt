package com.madcrew.pravamobil.models.responsemodels

data class PayHistoryResponse(
    var history: List<HistoryDate>? = null,
    var sum: String? = null,
    var status: String
)

data class HistoryDate(
    var date: String? = null,
    var price: Int? = null,
    var method: String? = null
)

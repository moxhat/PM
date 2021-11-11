package com.madcrew.pravamobil.models.responsemodels

data class TariffListResponse(
    var tariffs: List<Tariff>,
    var status: String
)

data class Tariff(
    var id: String,
    var title: String,
    var include: List<String>,
    var extra: List<String>,
    var price: String,
    var installment: String
)

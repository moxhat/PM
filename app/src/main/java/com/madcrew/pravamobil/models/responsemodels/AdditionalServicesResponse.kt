package com.madcrew.pravamobil.models.responsemodels

data class AdditionalServicesResponse(
    var prices: List<AdditionalService>? = null,
    var status: String? = null
)

data class AdditionalService(
    var id: String? = null,
    var title: String? = null,
    var price: Int? = null,
    var max: Int? = null
)
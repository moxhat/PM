package com.madcrew.pravamobil.models.submodels

data class MedInfoModel(
    var series: String?,
    var number: String?,
    var centre: String?,
    var license: String?,
    var date: String?,  // (YYYY-MM-DD)
    var status: String?
)

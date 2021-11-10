package com.madcrew.pravamobil.models.responsemodels

data class FilialResponse(
    val centres: List<Filial>? = null,
    val status: String
)

data class Filial(
    var id: String,
    var title: String,
    var full: String
)

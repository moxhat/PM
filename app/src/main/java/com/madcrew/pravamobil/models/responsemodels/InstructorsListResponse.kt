package com.madcrew.pravamobil.models.responsemodels

data class InstructorsListResponse(
    var instructors: List<Instructor>? = null,
    var status: String
)

data class Instructor(
    val id: String? = null,
    val name: String? = null,
    val secondName: String? = null,
    val patronymic: String? = null,
    val photoUrl: String? = null,
    val car: String? = null,
    val phone: String? = null,
    val rating: String? = null
)
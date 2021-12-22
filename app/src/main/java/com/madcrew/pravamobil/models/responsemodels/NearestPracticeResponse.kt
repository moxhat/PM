package com.madcrew.pravamobil.models.responsemodels

data class NearestPracticeResponse(
    var next: NearestPractice? = null,
    var status: String
)

data class NearestPractice(
    val date: String? = null,
    val time: String? = null,
    val time_id: String? = null,
    val place: String? = null,
    val instructor_id: String? = null,
    val name: String? = null,
    val secondName: String? = null,
    val patronymic: String? = null,
    val photoUrl: String? = null,
    val car: String? = null,
    val phone: String? = null,
    val status: String? = null,
    val instRating: String? = null,
    val rating: String? = null,
    val comment: String? = null,
)
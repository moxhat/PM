package com.madcrew.pravamobil.models.responsemodels

data class LessonHistoryResponse(
    var history: List<HistoryLessonModel>? = null,
    var status: String
)

data class HistoryLessonModel(
    var date: String? = null,
    var time: String? = null,
    var timeID: String? = null,
    var dateTIme: String? = null,
    var place: String? = null,
    var instructor_id: String? = null,
    var name: String? = null,
    var secondName: String? = null,
    var patronymic: String? = null,
    var photoUrl: String? = null,
    var car: String? = null,
    var phone: String? = null,
    var status: String? = null,
    var instRating: String? = null,
    var rating: String? = null
)
package com.madcrew.pravamobil.models.requestmodels

data class LessonRateRequest(
    var token: String,
    var school_id: Int,
    var client_id: Int,
    var instructor_id: Int,
    var date: String,
    var time_id: String,
    var features: List<String>,
    var comment: String,
    var rating: Int
)
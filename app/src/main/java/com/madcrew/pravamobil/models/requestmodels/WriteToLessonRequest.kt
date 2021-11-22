package com.madcrew.pravamobil.models.requestmodels

data class WriteToLessonRequest (
    var token: String,
    var school_id: String,
    var instructor_id: String,
    var client_id: String,
    var date: String,
    var time_id: String,
    var time_title: String
        )
package com.madcrew.pravamobil.models.requestmodels

data class LessonCancelRequest (
    var token: String? = null,
    var school_id: String? = null,
    var instructor_id: String? = null,
    var client_id: String? = null,
    var time_id: String? = null,
    var time_title: String? = null
        )
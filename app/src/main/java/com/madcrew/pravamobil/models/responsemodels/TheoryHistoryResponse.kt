package com.madcrew.pravamobil.models.responsemodels

data class TheoryHistoryResponse(
    var schedule: List<TheoryLesson>? = null,
    var status: String
)

//Назначено/Посещено/Неявка
data class TheoryLesson(
    var date: String? = null,
    var time: String? = null,
    var status: String? = null,
    var name: String? = null,
    var secondName: String? = null,
    var patronymic: String? = null,
    var photoUrl: String? = null,
    var phone: String? = null,
    var place: String? = null
)

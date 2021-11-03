package com.madcrew.pravamobil.models.responsemodels

import com.google.gson.annotations.SerializedName

data class SchoolListResponse(
    @SerializedName("schools")
    var schoolList: MutableList<School>,
    var status: String

)

data class School(
    var id: String,
    var title: String
)
package com.madcrew.pravamobil.models.requestmodels

import com.google.gson.annotations.SerializedName

data class CategoryRequest(
    var token: String,
    @SerializedName("school_id")
    var schoolId: String,
)
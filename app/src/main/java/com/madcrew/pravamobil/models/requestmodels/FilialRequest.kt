package com.madcrew.pravamobil.models.requestmodels

import com.google.gson.annotations.SerializedName

data class FilialRequest(
    val token: String,
    @SerializedName("school_id")
    val schoolId: String
)

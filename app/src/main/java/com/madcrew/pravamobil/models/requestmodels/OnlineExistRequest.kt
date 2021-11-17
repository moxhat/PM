package com.madcrew.pravamobil.models.requestmodels

import com.google.gson.annotations.SerializedName

data class OnlineExistRequest(
    var token: String,
    @SerializedName("school_id")
    var schoolId: String
)

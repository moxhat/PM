package com.madcrew.pravamobil.models.requestmodels

import com.google.gson.annotations.SerializedName

data class CallCodeRequest(
    @SerializedName("token")
    var token: String,
    @SerializedName("phone")
    var phoneNumber: String,
    @SerializedName("hwid")
    var hwid: String
)

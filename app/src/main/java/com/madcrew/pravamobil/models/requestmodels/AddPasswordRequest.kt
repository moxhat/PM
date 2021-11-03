package com.madcrew.pravamobil.models.requestmodels

import com.google.gson.annotations.SerializedName

data class AddPasswordRequest(
    var token: String,
    @SerializedName("school_id")
    var schoolId: String,
    var name: String,
    @SerializedName("phone")
    var phoneNumber: String,
    var password: String
)

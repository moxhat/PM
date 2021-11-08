package com.madcrew.pravamobil.models.requestmodels

import com.google.gson.annotations.SerializedName

data class ClientAuthorizationRequest(
    var token: String,
    @SerializedName("phone")
    var phoneNumber: String,
    var password: String,
)



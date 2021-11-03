package com.madcrew.pravamobil.models.responsemodels

import com.google.gson.annotations.SerializedName

data class AddPasswordResponse(
    @SerializedName("id")
    var clientId: String,
    var status: String
)

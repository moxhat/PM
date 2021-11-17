package com.madcrew.pravamobil.models.requestmodels

import com.google.gson.annotations.SerializedName

data class ClientInfoRequest(
    var token: String,
    @SerializedName("school_id")
    var schoolId: String,
    @SerializedName("client_id")
    var clientId: String,
    var cells: List<String>

)

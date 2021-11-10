package com.madcrew.pravamobil.models.requestmodels

import com.google.gson.annotations.SerializedName

data class GroupsRequest(
    var token: String,
    @SerializedName("school_id")
    var schoolId: String,
    @SerializedName("centre_id")
    var filialId: String
)

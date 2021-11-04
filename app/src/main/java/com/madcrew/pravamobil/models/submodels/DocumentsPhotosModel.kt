package com.madcrew.pravamobil.models.submodels

import com.google.gson.annotations.SerializedName

data class DocumentsPhotosModel(
    @SerializedName("photo")
    var avatar: String?,
    var firstPage: String?,
    var secondPage: String?,
    var snils: String?,
    var medical: String?

)

package com.madcrew.pravamobil.models.submodels

import com.google.gson.annotations.SerializedName

data class DocumentsPhotosModel(
    @SerializedName("photo")
    var avatar: String? = null,
    var firstPage: String? = null,
    var secondPage: String? = null,
    var snils: String? = null,
    var medical: String? = null

)

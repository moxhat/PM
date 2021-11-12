package com.madcrew.pravamobil.models.submodels

import com.google.gson.annotations.SerializedName

data class ParentModel(
    var lastName: String? = null,
    var name: String? = null,
    @SerializedName("patronymic")
    var thirdName: String? = null,
    @SerializedName("dateBirthday")
    var dateBirthday: String? = null,
    var phoneNumber: String? = null,
    var passport: PassportModel? = null,
    @SerializedName("place")
    var address: AddressModel? = null
)

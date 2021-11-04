package com.madcrew.pravamobil.models.submodels

import com.google.gson.annotations.SerializedName

data class ParentModel(
    var lastName: String?,
    var name: String?,
    @SerializedName("patronymic")
    var thirdName: String?,
    @SerializedName("dateBirthday")
    var dateBirthday: String?,
    var phoneNumber: String?,
    var passport: PassportModel?,
    @SerializedName("place")
    var address: AddressModel?
)

package com.madcrew.pravamobil.models.requestmodels

import com.google.gson.annotations.SerializedName
import com.madcrew.pravamobil.models.submodels.*

data class FullRegistrationRequest(
    var token: String,
    @SerializedName("client_id")
    var clientId: String,
    @SerializedName("school_id")
    var schoolId: String,
    var lastName: String?,
    var name: String?,
    var patronymic: String?,
    var dateBirthday: String?,
    var phoneNumber: String?,
    var password: String?,
    var progress: String?,
    var email: String?,
    var group_id: String?,
    var centre: String?,
    var kpp: String?,
    var format: String?,
    var category: String?,
    var snils: String?,
    var tariffId: String?,
    var passport: PassportModel?,
    @SerializedName("place")
    var address: AddressModel?,
    @SerializedName("living")
    var livingAddress: AddressModel?,
    var images: DocumentsPhotosModel?,
    var parent: ParentModel?,
    var contractType: String?,
    var documentType: String?,
    var discount: String?,
    var pay: PaymentModel?,
    var medInfo: MedInfoModel?,
    var stats: String?,
    var dogovor: String?
    )

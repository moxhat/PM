package com.madcrew.pravamobil.models.requestmodels

import com.google.gson.annotations.SerializedName
import com.madcrew.pravamobil.models.submodels.*

data class FullRegistrationRequest(
    var token: String,
    @SerializedName("client_id")
    var clientId: String,
    @SerializedName("school_id")
    var schoolId: String,
    var new_school_id: String? = null,
    var lastName: String? = null,
    var name: String? = null,
    var patronymic: String? = null,
    var dateBirthday: String? = null,
    var phoneNumber: String? = null,
    var password: String? = null,
    var appProgress: String? = null,
    var email: String? = null,
    var group_id: String? = null,
    var centre_id: String? = null,
    var kpp: String? = null,
    var format: String? = null,
    var category: String? = null,
    var snils: String? = null,
    var tariffId: String? = null,
    var passport: PassportModel? = null,
    @SerializedName("place")
    var address: AddressModel? = null,
    @SerializedName("living")
    var livingAddress: AddressModel? = null,
    var images: DocumentsPhotosModel? = null,
    var parent: ParentModel? = null,
    var contractType: String? = null,
    var documentType: String? = null,
    var discount: String? = null,
    var pay: PaymentModel? = null,
    var medInfo: MedInfoModel? = null,
    var stats: String? = null,
    var dogovor: String? = null
    )

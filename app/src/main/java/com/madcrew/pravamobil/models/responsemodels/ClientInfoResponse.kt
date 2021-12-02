package com.madcrew.pravamobil.models.responsemodels

import com.google.gson.annotations.SerializedName
import com.madcrew.pravamobil.models.submodels.AddressModel
import com.madcrew.pravamobil.models.submodels.DocumentsPhotosModel
import com.madcrew.pravamobil.models.submodels.ParentModel
import com.madcrew.pravamobil.models.submodels.PassportModel

data class ClientInfoResponse(
    var client: Client? = null,
    var status: String
)

data class Client(
    var lastName: String? = null,
    var name: String? = null,
    var patronymic: String? = null,
    var dateBirthday: String? = null,
    var adult: String? = null,
    var phoneNumber: String? = null,
    var progress: String? = null,
    var email: String? = null,
    var group_id: String? = null,
    var centre_id: String? = null,
    var instructor: String? = null,
    var kpp: String? = null,
    var format: String? = null,
    var category: String? = null,
    var snils: String? = null,
    var other: String? = null,
    var passport: PassportModel? = null,
    var place: AddressModel? = null,
    var living: AddressModel? = null,
    var images: DocumentsPhotosModel? = null,
    var parent: ParentModel? = null,
    var contractType: String? = null,
    var documentType: String? = null,
    var tariffId: String? = null,
    var price: String? = null,
    var pay: String? = null,
    var discount: String? = null,
    @SerializedName("medinfo")
    var spravka: String? = null,
    var stats: String? = null,
    @SerializedName("examens")
    var exams: String? = null,
    var practice: String? = null,
    @SerializedName("dogovor")
    var contract: String? = null,
    var tester: String? = null,
    var archive: String? = null
)

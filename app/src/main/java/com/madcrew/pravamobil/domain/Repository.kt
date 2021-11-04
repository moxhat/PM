package com.madcrew.pravamobil.domain

import com.madcrew.pravamobil.models.requestmodels.*
import com.madcrew.pravamobil.models.responsemodels.*
import retrofit2.Response

class Repository {
    suspend fun getSchoolList(schoolListRequest: TokenOnly): Response<SchoolListResponse>{
        return RetrofitInstance.api.getSchoolList(schoolListRequest)
    }

    suspend fun getCallCode(callCodeRequest: CallCodeRequest): Response<CallCodeResponse>{
        return RetrofitInstance.api.getCallCode(callCodeRequest)
    }

    suspend fun addPassword(addPasswordRequest: AddPasswordRequest): Response<AddPasswordResponse>{
        return RetrofitInstance.api.addPassword(addPasswordRequest)
    }

    suspend fun setFullRegistration(fullRegistrationRequest: FullRegistrationRequest): Response<StatusOnlyResponse>{
        return RetrofitInstance.api.setFullRegistration(fullRegistrationRequest)
    }

    suspend fun updateProgress(progressRequest: ProgressRequest): Response<StatusOnlyResponse>{
        return RetrofitInstance.api.updateProgress(progressRequest)
    }

    suspend fun getTariffList(tariffListRequest: TariffListRequest): Response<TariffListResponse>{
        return RetrofitInstance.api.getTariffList(tariffListRequest)
    }

    suspend fun getCategoryList(categoryRequest: CategoryRequest): Response<CategoryResponse>{
        return RetrofitInstance.api.getCategoryList(categoryRequest)
    }

    suspend fun getTransmissionInfo(transmissionInfoRequest: TransmissionInfoRequest): Response<TransmissionInfoResponse>{
        return RetrofitInstance.api.getTransmissionInfo(transmissionInfoRequest)
    }

    suspend fun clientAuthorization(clientAuthorizationRequest: ClientAuthorizationRequest): Response<ClientAuthorizationResponse>{
        return RetrofitInstance.api.clientAuthorization(clientAuthorizationRequest)
    }
}
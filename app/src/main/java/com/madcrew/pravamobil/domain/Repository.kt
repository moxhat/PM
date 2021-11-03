package com.madcrew.pravamobil.domain

import com.madcrew.pravamobil.models.requestmodels.AddPasswordRequest
import com.madcrew.pravamobil.models.requestmodels.CallCodeRequest
import com.madcrew.pravamobil.models.requestmodels.TokenOnly
import com.madcrew.pravamobil.models.responsemodels.AddPasswordResponse
import com.madcrew.pravamobil.models.responsemodels.CallCodeResponse
import com.madcrew.pravamobil.models.responsemodels.SchoolListResponse
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
}
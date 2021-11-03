package com.madcrew.pravamobil.domain

import com.madcrew.pravamobil.models.requestmodels.AddPasswordRequest
import com.madcrew.pravamobil.models.requestmodels.CallCodeRequest
import com.madcrew.pravamobil.models.requestmodels.TokenOnly
import com.madcrew.pravamobil.models.responsemodels.AddPasswordResponse
import com.madcrew.pravamobil.models.responsemodels.CallCodeResponse
import com.madcrew.pravamobil.models.responsemodels.SchoolListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PMApi {

    // Получение списка автошкол
    @POST("/api/school/list")
    suspend fun getSchoolList(
        @Body
        schoolListRequest: TokenOnly
    ): Response<SchoolListResponse>

    // Получение СМС кода
    @POST("/api/client/callme")
    suspend fun getCallCode(
        @Body
        callCodeRequest: CallCodeRequest
    ): Response<CallCodeResponse>

    // Первичная регистрация
    @POST("/api/client/register")
    suspend fun addPassword(
        @Body
        addPasswordRequest: AddPasswordRequest
    ): Response<AddPasswordResponse>
}
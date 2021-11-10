package com.madcrew.pravamobil.domain

import com.madcrew.pravamobil.models.requestmodels.*
import com.madcrew.pravamobil.models.responsemodels.*
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

    //Авторизация клиента
    @POST("/api/client/auth")
    suspend fun clientAuthorization(
        @Body
        clientAuthorizationRequest: ClientAuthorizationRequest
    ): Response<ClientAuthorizationResponse>

    // Первичная регистрация
    @POST("/api/client/register")
    suspend fun addPassword(
        @Body
        addPasswordRequest: AddPasswordRequest
    ): Response<AddPasswordResponse>

    // Полная регистрация
    @POST("/api/client/register")
    suspend fun setFullRegistration(
        @Body
        fullRegistrationRequest: FullRegistrationRequest
    ): Response<StatusOnlyResponse>

    // Обновлене прогресса
    @POST("/api/client/progress")
    suspend fun updateProgress(
        @Body
        progressRequest: ProgressRequest
    ): Response<StatusOnlyResponse>

    // Получение списка тарифов
    @POST("/api/school/tariff/list")
    suspend fun getTariffList(
        @Body
        tariffListRequest: TariffListRequest
    ): Response<TariffListResponse>

    //Получение списка категорий доступных автошколе
    @POST("/api/school/category/list")
    suspend fun getCategoryList(
        @Body
        categoryRequest: CategoryRequest
    ): Response<CategoryResponse>

    //Получение наличия кпп для выбранной категории
    @POST("/api/school/category/info")
    suspend fun getTransmissionInfo(
        @Body
        transmissionInfoRequest: TransmissionInfoRequest
    ): Response<TransmissionInfoResponse>

    //Получение списка филиалов
    @POST("/api/school/centre/list")
    suspend fun getFilialList(
        @Body
        filialRequest: FilialRequest
    ): Response<FilialResponse>

    //Получение списка групп филиала
    @POST("/api/school/group/list")
    suspend fun getGroupList(
        @Body
        groupsRequest: GroupsRequest
    ): Response<GroupsResponse>
}
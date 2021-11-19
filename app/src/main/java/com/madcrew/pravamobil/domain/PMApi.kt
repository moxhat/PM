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

    //Получение информации о клиенте
    @POST("/api/client/info")
    suspend fun getClientInfo(
        @Body
        gClientInfoRequest: ClientInfoRequest
    ): Response<ClientInfoResponse>

    //Проверка наличия онлайн группы
    @POST("/api/school/group/online/exist")
    suspend fun getOnlineExist(
        @Body
        onlineExistRequest: OnlineExistRequest
    ): Response<OnlineExistResponse>

    //Проверка статуса справки
    @POST("/api/client/medical/status")
    suspend fun getSpravkaStatus(
        @Body
        spravkaStatusRequest: SpravkaStatusRequest
    ): Response<SpravkaStatusResponse>

    //Удаление справки
    @POST("/api/client/medical/delete")
    suspend fun deleteSpravka(
        @Body
        spravkaStatusRequest: SpravkaStatusRequest
    ): Response<StatusOnlyResponse>

    //Получение списка инструкторов назначенных клиенту
    @POST("/api/client/instructor/list")
    suspend fun getInstructorsList(
        @Body
        spravkaStatusRequest: SpravkaStatusRequest
    ): Response<InstructorsListResponse>

    //Получение списка доступных занятий для записи
    @POST("/api/instructor/available/times")
    suspend fun getAvailableTimes(
        @Body
        availableTimesRequest: AvailableTimesRequest
    ): Response<AvailableTimesResponse>
}
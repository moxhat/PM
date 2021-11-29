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

    // Получение стоимости выбранного тарифа
    @POST("/api/school/tariff/info")
    suspend fun getTariffPrice(
        @Body
        spravkaStatusRequest: SpravkaStatusRequest
    ): Response<TariffPriceResponse>

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

    //Запись на практические занятия
    @POST("/api/instructor/learn/write")
    suspend fun writeToLesson(
        @Body
        writeToLessonRequest: WriteToLessonRequest
    ): Response<StatusWithErrorResponse>

    //История занятий практики
    @POST("/api/instructor/learn/history")
    suspend fun getPracticeLessonHistory(
        @Body
        spravkaStatusRequest: SpravkaStatusRequest
    ): Response<LessonHistoryResponse>

    //Отмена занятия
    @POST("/api/instructor/learn/cancel")
    suspend fun setLessonCancel(
        @Body
        lessonCancelRequest: LessonCancelRequest
    ): Response<StatusWithErrorResponse>

    //Создание счета на оплату, генерация договора и отправка на почту
    @POST("/api/client/contract/create")
    suspend fun getContract(
        @Body
        contractRequest: ContractRequest
    ): Response<StatusOnlyResponse>


    //Получение статуса платежа
    @POST("/api/pay/status")
    suspend fun getPaymentStatus(
        @Body
        chekPaymentStatusRequest: ChekPaymentStatusRequest
    ): Response<ChekPaymentStatusResponse>

    //Создание платежа + оплата
    @POST("/api/pay/create")
    suspend fun createPayment(
        @Body
        createPaymentRequest: CreatePaymentRequest
    ): Response<CreatePaymentResponse>

    //оценка занятия
    @POST("/api/instructor/learn/rate")
    suspend fun setLessonRate(
        @Body
        lessonRateRequest: LessonRateRequest
    ): Response<StatusOnlyResponse>
}
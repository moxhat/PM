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

    suspend fun getTariffPrice(spravkaStatusRequest: SpravkaStatusRequest): Response<TariffPriceResponse>{
        return RetrofitInstance.api.getTariffPrice(spravkaStatusRequest)
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

    suspend fun getFilialList(filialRequest: FilialRequest): Response<FilialResponse>{
        return RetrofitInstance.api.getFilialList(filialRequest)
    }

    suspend fun getGroupList(groupsRequest: GroupsRequest): Response<GroupsResponse>{
        return RetrofitInstance.api.getGroupList(groupsRequest)
    }

    suspend fun getClientInfo(clientInfoRequest: ClientInfoRequest): Response<ClientInfoResponse>{
        return RetrofitInstance.api.getClientInfo(clientInfoRequest)
    }

    suspend fun getOnlineExist(onlineExistRequest: OnlineExistRequest): Response<OnlineExistResponse>{
        return RetrofitInstance.api.getOnlineExist(onlineExistRequest)
    }

    suspend fun getSpravkaStatus(spravkaStatusRequest: SpravkaStatusRequest): Response<SpravkaStatusResponse>{
        return RetrofitInstance.api.getSpravkaStatus(spravkaStatusRequest)
    }

    suspend fun deleteSpravka(spravkaStatusRequest: SpravkaStatusRequest): Response<StatusOnlyResponse>{
        return RetrofitInstance.api.deleteSpravka(spravkaStatusRequest)
    }

    suspend fun getInstructorsList(spravkaStatusRequest: SpravkaStatusRequest): Response<InstructorsListResponse>{
        return RetrofitInstance.api.getInstructorsList(spravkaStatusRequest)
    }

    suspend fun getAvailableTimes(availableTimesRequest: AvailableTimesRequest): Response<AvailableTimesResponse>{
        return RetrofitInstance.api.getAvailableTimes(availableTimesRequest)
    }

    suspend fun writeToLesson(writeToLessonRequest: WriteToLessonRequest): Response<StatusWithErrorResponse>{
        return RetrofitInstance.api.writeToLesson(writeToLessonRequest)
    }

    suspend fun getPracticeLessonHistory(spravkaStatusRequest: SpravkaStatusRequest): Response<LessonHistoryResponse>{
        return RetrofitInstance.api.getPracticeLessonHistory(spravkaStatusRequest)
    }

    suspend fun setLessonCancel(lessonCancelRequest: LessonCancelRequest): Response<StatusWithErrorResponse>{
        return RetrofitInstance.api.setLessonCancel(lessonCancelRequest)
    }

    suspend fun getContract(contractRequest: ContractRequest): Response<StatusOnlyResponse> {
        return RetrofitInstance.api.getContract(contractRequest)
    }

    suspend fun createPayment(createPaymentRequest: CreatePaymentRequest): Response<CreatePaymentResponse> {
        return RetrofitInstance.api.createPayment(createPaymentRequest)
    }

    suspend fun getPaymentStatus(chekPaymentStatusRequest: ChekPaymentStatusRequest): Response<ChekPaymentStatusResponse> {
        return RetrofitInstance.api.getPaymentStatus(chekPaymentStatusRequest )
    }

    suspend fun setLessonRate(lessonRateRequest: LessonRateRequest): Response<StatusOnlyResponse> {
        return RetrofitInstance.api.setLessonRate(lessonRateRequest)
    }
}
package com.madcrew.pravamobil.view.activity.progress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.*
import com.madcrew.pravamobil.models.responsemodels.*
import kotlinx.coroutines.launch
import retrofit2.Response

class ProgressViewModel (private val repository: Repository): ViewModel() {

    var updateProgressResponse = MutableLiveData<Response<StatusOnlyResponse>>()
    var registrationResponse = MutableLiveData<Response<StatusOnlyResponse>>()
    var clientInfo = MutableLiveData<Response<ClientInfoResponse>>()
    var onlineExist = MutableLiveData<Response<OnlineExistResponse>>()
    var tariffInfo = MutableLiveData<Response<TariffPriceResponse>>()
    var createPayment = MutableLiveData<Response<CreatePaymentResponse>>()
    var paymentStatus = MutableLiveData<Response<ChekPaymentStatusResponse>>()

    fun updateProgress(progressRequest: ProgressRequest) {
        viewModelScope.launch {
            val response = repository.updateProgress(progressRequest)
            updateProgressResponse.value = response
        }
    }

    fun updateClientData(fullRegistrationRequest: FullRegistrationRequest) {
        viewModelScope.launch {
            val response = repository.setFullRegistration(fullRegistrationRequest)
            registrationResponse.value = response
        }
    }

    fun getClientInfo(clientInfoRequest: ClientInfoRequest) {
        viewModelScope.launch {
            val response = repository.getClientInfo(clientInfoRequest)
            clientInfo.value = response
        }
    }

    fun getOnlineExist(onlineExistRequest: OnlineExistRequest){
        viewModelScope.launch {
            val response = repository.getOnlineExist(onlineExistRequest)
            onlineExist.value = response
        }
    }

    fun getContract(contractRequest: ContractRequest){
        viewModelScope.launch {
            val response = repository.getContract(contractRequest)
        }
    }

    fun getTariffInfo(spravkaStatusRequest: SpravkaStatusRequest){
        viewModelScope.launch {
            val response = repository.getTariffPrice(spravkaStatusRequest)
            tariffInfo.value = response
        }
    }

    fun createNewPayment(createPaymentRequest: CreatePaymentRequest){
        viewModelScope.launch {
            val response = repository.createPayment(createPaymentRequest)
            createPayment.value = response
        }
    }

    fun chekPaymentStatus(chekPaymentStatusRequest: ChekPaymentStatusRequest){
        viewModelScope.launch {
            val response = repository.getPaymentStatus(chekPaymentStatusRequest)
            paymentStatus.value = response
        }
    }
}
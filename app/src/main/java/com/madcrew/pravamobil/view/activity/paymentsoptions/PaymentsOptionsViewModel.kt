package com.madcrew.pravamobil.view.activity.paymentsoptions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.AdditionalPaymentRequest
import com.madcrew.pravamobil.models.requestmodels.CreatePaymentRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.models.responsemodels.AdditionalPaymentResponse
import com.madcrew.pravamobil.models.responsemodels.AdditionalServicesResponse
import com.madcrew.pravamobil.models.responsemodels.CreatePaymentResponse
import com.madcrew.pravamobil.models.responsemodels.PayHistoryResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class PaymentsOptionsViewModel (private val repository: Repository): ViewModel() {
    var createPayment = MutableLiveData<Response<CreatePaymentResponse>>()

    fun createNewPayment(createPaymentRequest: CreatePaymentRequest){
        viewModelScope.launch {
            val response = repository.createPayment(createPaymentRequest)
            createPayment.value = response
        }
    }

    var paymentHistory = MutableLiveData<Response<PayHistoryResponse>>()

    fun getPaymentHistory(spravkaStatusRequest: SpravkaStatusRequest){
        viewModelScope.launch {
            val response = repository.getPayHistory(spravkaStatusRequest)
            paymentHistory.value = response
        }
    }
}
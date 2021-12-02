package com.madcrew.pravamobil.view.activity.paymentsoptions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.CreatePaymentRequest
import com.madcrew.pravamobil.models.responsemodels.CreatePaymentResponse
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
}
package com.madcrew.pravamobil.view.fragment.education.payments.additionalpayments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.AdditionalPaymentRequest
import com.madcrew.pravamobil.models.requestmodels.CategoryRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.models.responsemodels.AdditionalPaymentResponse
import com.madcrew.pravamobil.models.responsemodels.AdditionalServicesResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class AdditionalPaymentsViewModel (private val repository: Repository): ViewModel() {

    var additionalServices = MutableLiveData<Response<AdditionalServicesResponse>>()

    fun getAdditionalServices(spravkaStatusRequest: SpravkaStatusRequest) {
        viewModelScope.launch {
            val response = repository.getAdditionalServices(spravkaStatusRequest)
            additionalServices.value = response
        }
    }

    var additionalPayment = MutableLiveData<Response<AdditionalPaymentResponse>>()

    fun createAdditionalPayment(additionalPaymentRequest: AdditionalPaymentRequest){
        viewModelScope.launch {
            val response = repository.createAdditionalPayment(additionalPaymentRequest)
            additionalPayment.value = response
        }
    }
}
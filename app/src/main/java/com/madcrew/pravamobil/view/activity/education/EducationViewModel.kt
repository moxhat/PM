package com.madcrew.pravamobil.view.activity.education

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.CreatePaymentRequest
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.requestmodels.ProgressRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.models.responsemodels.*
import kotlinx.coroutines.launch
import retrofit2.Response

class EducationViewModel  (private val repository: Repository): ViewModel() {
    var spravkaStatus = MutableLiveData<Response<SpravkaStatusResponse>>()

    var updateProgressResponse = MutableLiveData<Response<StatusOnlyResponse>>()

    fun getSpravkaStatus(spravkaStatusRequest: SpravkaStatusRequest) {
        viewModelScope.launch {
            val response = repository.getSpravkaStatus(spravkaStatusRequest)
            spravkaStatus.value = response
        }
    }

    var spravkaDeleted = MutableLiveData<Response<StatusOnlyResponse>>()

    fun deleteSpravka(spravkaStatusRequest: SpravkaStatusRequest) {
        viewModelScope.launch {
            val response = repository.deleteSpravka(spravkaStatusRequest)
            spravkaDeleted.value = response
        }
    }

    var registrationResponse = MutableLiveData<Response<StatusOnlyResponse>>()

    fun updateClientData(fullRegistrationRequest: FullRegistrationRequest) {
        viewModelScope.launch {
            val response = repository.setFullRegistration(fullRegistrationRequest)
            registrationResponse.value = response
        }
    }

    fun updateProgress(progressRequest: ProgressRequest) {
        viewModelScope.launch {
            val response = repository.updateProgress(progressRequest)
            updateProgressResponse.value = response
        }
    }

    var createPayment = MutableLiveData<Response<CreatePaymentResponse>>()

    fun createNewPayment(createPaymentRequest: CreatePaymentRequest){
        viewModelScope.launch {
            val response = repository.createPayment(createPaymentRequest)
            createPayment.value = response
        }
    }


}
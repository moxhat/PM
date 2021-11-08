package com.madcrew.pravamobil.view.activity.progress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.requestmodels.ProgressRequest
import com.madcrew.pravamobil.models.responsemodels.StatusOnlyResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class ProgressViewModel (private val repository: Repository): ViewModel() {

    var updateProgressResponse = MutableLiveData<Response<StatusOnlyResponse>>()
    var registrationResponse = MutableLiveData<Response<StatusOnlyResponse>>()

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
}
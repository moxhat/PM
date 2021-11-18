package com.madcrew.pravamobil.view.fragment.registration.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.ClientAuthorizationRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.models.responsemodels.ClientAuthorizationResponse
import com.madcrew.pravamobil.models.responsemodels.SpravkaStatusResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class SignInViewModel (private val repository: Repository): ViewModel() {
    var signResponse = MutableLiveData<Response<ClientAuthorizationResponse>>()

    fun clientAuthorization(clientAuthorizationRequest: ClientAuthorizationRequest) {
        viewModelScope.launch {
            val response = repository.clientAuthorization(clientAuthorizationRequest)
            signResponse.value = response
        }
    }
}
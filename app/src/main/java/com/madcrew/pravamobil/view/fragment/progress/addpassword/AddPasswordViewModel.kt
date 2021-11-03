package com.madcrew.pravamobil.view.fragment.progress.addpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.AddPasswordRequest
import com.madcrew.pravamobil.models.responsemodels.AddPasswordResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class AddPasswordViewModel (private val repository: Repository): ViewModel() {

    val firstRegistrationResponse: MutableLiveData<Response<AddPasswordResponse>> = MutableLiveData()

    fun addPassword(addPasswordRequest: AddPasswordRequest) {
        viewModelScope.launch {
            val response = repository.addPassword(addPasswordRequest)
            firstRegistrationResponse.value = response
        }
    }
}
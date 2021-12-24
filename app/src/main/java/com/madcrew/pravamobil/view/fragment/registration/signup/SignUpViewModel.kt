package com.madcrew.pravamobil.view.fragment.registration.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.CallCodeRequest
import com.madcrew.pravamobil.models.responsemodels.CallCodeResponse
import com.madcrew.pravamobil.utils.setEnable
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*
import kotlin.concurrent.timerTask

class SignUpViewModel (private val repository: Repository): ViewModel() {

    val smsCodeResponse: MutableLiveData<Response<CallCodeResponse>> = MutableLiveData()

    fun getSmsCode(callCodeRequest: CallCodeRequest) {
        viewModelScope.launch {
            val response = repository.getCallCode(callCodeRequest)
            smsCodeResponse.value = response
        }
    }
}
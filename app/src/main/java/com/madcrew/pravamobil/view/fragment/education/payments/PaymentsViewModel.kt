package com.madcrew.pravamobil.view.fragment.education.payments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.models.responsemodels.PayInfoResponse
import com.madcrew.pravamobil.models.responsemodels.SpravkaStatusResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class PaymentsViewModel (private val repository: Repository): ViewModel() {

    var payInfo = MutableLiveData<Response<PayInfoResponse>>()

    fun getPayInfo(spravkaStatusRequest: SpravkaStatusRequest) {
        viewModelScope.launch {
            val response = repository.getPayInfo(spravkaStatusRequest)
            payInfo.value = response
        }
    }
}
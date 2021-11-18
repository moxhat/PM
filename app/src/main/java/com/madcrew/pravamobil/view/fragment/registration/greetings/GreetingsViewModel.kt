package com.madcrew.pravamobil.view.fragment.registration.greetings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.models.responsemodels.SpravkaStatusResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class GreetingsViewModel(private val repository: Repository): ViewModel() {
    var spravkaStatus = MutableLiveData<Response<SpravkaStatusResponse>>()

    fun getSpravkaStatus(spravkaStatusRequest: SpravkaStatusRequest) {
        viewModelScope.launch {
            val response = repository.getSpravkaStatus(spravkaStatusRequest)
            spravkaStatus.value = response
        }
    }
}
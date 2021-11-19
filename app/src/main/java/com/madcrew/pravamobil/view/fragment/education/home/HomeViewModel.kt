package com.madcrew.pravamobil.view.fragment.education.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.models.responsemodels.SpravkaStatusResponse
import com.madcrew.pravamobil.models.responsemodels.StatusOnlyResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val repository: Repository): ViewModel() {

    var spravkaDeleted = MutableLiveData<Response<StatusOnlyResponse>>()

    fun deleteSpravka(spravkaStatusRequest: SpravkaStatusRequest) {
        viewModelScope.launch {
            val response = repository.deleteSpravka(spravkaStatusRequest)
            spravkaDeleted.value = response
        }
    }

    var spravkaStatus = MutableLiveData<Response<SpravkaStatusResponse>>()

    fun getSpravkaStatus(spravkaStatusRequest: SpravkaStatusRequest) {
        viewModelScope.launch {
            val response = repository.getSpravkaStatus(spravkaStatusRequest)
            spravkaStatus.value = response
        }
    }
}
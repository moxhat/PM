package com.madcrew.pravamobil.view.fragment.progress.tariff

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.TariffListRequest
import com.madcrew.pravamobil.models.responsemodels.TariffListResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class TariffViewModel (private val repository: Repository): ViewModel() {

    var tariffResponse = MutableLiveData<Response<TariffListResponse>>()

    fun getTariffList(tariffListRequest: TariffListRequest) {
        viewModelScope.launch {
            val response = repository.getTariffList(tariffListRequest)
            tariffResponse.value = response
        }
    }
}
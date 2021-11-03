package com.madcrew.pravamobil.view.fragment.registration.school

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.TokenOnly
import com.madcrew.pravamobil.models.responsemodels.SchoolListResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class SchoolViewModel(private val repository: Repository): ViewModel() {

    val schoolListResponse: MutableLiveData<Response<SchoolListResponse>> = MutableLiveData()

    fun getSchoolList(schoolListRequest: TokenOnly) {
        viewModelScope.launch {
            val response = repository.getSchoolList(schoolListRequest)
            schoolListResponse.value = response
        }
    }
}
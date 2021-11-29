package com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.openlesson

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.LessonRateRequest
import com.madcrew.pravamobil.models.responsemodels.StatusOnlyResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class OpenLessonViewModel (private val repository: Repository): ViewModel() {

    var lessonRateResponse = MutableLiveData<Response<StatusOnlyResponse>>()

    fun setLessonRate(lessonRateRequest: LessonRateRequest) {
        viewModelScope.launch {
            val response = repository.setLessonRate(lessonRateRequest)
            lessonRateResponse.value = response
        }
    }
}
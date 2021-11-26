package com.madcrew.pravamobil.view.activity.practiceoptions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.LessonCancelRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.models.responsemodels.LessonHistoryResponse
import com.madcrew.pravamobil.models.responsemodels.StatusWithErrorResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class PracticeOptionViewModel (private val repository: Repository): ViewModel() {

    var lessonHistoryPracticeResponse = MutableLiveData<Response<LessonHistoryResponse>>()

    fun getPracticeHistory(spravkaStatusRequest: SpravkaStatusRequest) {
        viewModelScope.launch {
            val response = repository.getPracticeLessonHistory(spravkaStatusRequest)
            lessonHistoryPracticeResponse.value = response
        }
    }

    var lessonCancelResponse = MutableLiveData<Response<StatusWithErrorResponse>>()

    fun setLessonCancel(lessonCancelRequest: LessonCancelRequest) {
        viewModelScope.launch {
            val response = repository.setLessonCancel(lessonCancelRequest)
            lessonCancelResponse.value = response
        }
    }
}
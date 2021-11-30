package com.madcrew.pravamobil.view.fragment.education.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.LessonCancelRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.models.responsemodels.*
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

    var lessonHistoryPracticeResponse = MutableLiveData<Response<LessonHistoryResponse>>()

    fun getPracticeHistory(spravkaStatusRequest: SpravkaStatusRequest) {
        viewModelScope.launch {
            val response = repository.getPracticeLessonHistory(spravkaStatusRequest)
            lessonHistoryPracticeResponse.value = response
        }
    }


    val theoryHistory = MutableLiveData<Response<TheoryHistoryResponse>>()

    fun getTheoryHistory(spravkaStatusRequest: SpravkaStatusRequest){
        viewModelScope.launch {
            val response = repository.getTheoryHistory(spravkaStatusRequest)
            theoryHistory.value = response
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
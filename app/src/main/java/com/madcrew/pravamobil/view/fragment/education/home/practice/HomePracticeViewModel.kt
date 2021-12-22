package com.madcrew.pravamobil.view.fragment.education.home.practice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.LessonCancelRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.models.responsemodels.InstructorsListResponse
import com.madcrew.pravamobil.models.responsemodels.LessonHistoryResponse
import com.madcrew.pravamobil.models.responsemodels.NearestPracticeResponse
import com.madcrew.pravamobil.models.responsemodels.StatusWithErrorResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class HomePracticeViewModel (private val repository: Repository): ViewModel() {

    var instructorsResponse = MutableLiveData<Response<InstructorsListResponse>>()

    fun getInstructors(spravkaStatusRequest: SpravkaStatusRequest) {
        viewModelScope.launch {
            val response = repository.getInstructorsList(spravkaStatusRequest)
            instructorsResponse.value = response
        }
    }

    var lessonCancelResponse = MutableLiveData<Response<StatusWithErrorResponse>>()

    fun setLessonCancel(lessonCancelRequest: LessonCancelRequest) {
        viewModelScope.launch {
            val response = repository.setLessonCancel(lessonCancelRequest)
            lessonCancelResponse.value = response
        }
    }
//    var spravkaStatus = MutableLiveData<Response<SpravkaStatusResponse>>()
//
//    fun getSpravkaStatus(spravkaStatusRequest: SpravkaStatusRequest) {
//        viewModelScope.launch {
//            val response = repository.getSpravkaStatus(spravkaStatusRequest)
//            spravkaStatus.value = response
//        }
//    }
}
package com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.AvailableTimesRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.models.requestmodels.WriteToLessonRequest
import com.madcrew.pravamobil.models.responsemodels.AvailableTimesResponse
import com.madcrew.pravamobil.models.responsemodels.InstructorsListResponse
import com.madcrew.pravamobil.models.responsemodels.StatusWithErrorResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class DrivingRecordViewModel (private val repository: Repository): ViewModel() {

    var instructorsResponse = MutableLiveData<Response<InstructorsListResponse>>()

    fun getInstructors(spravkaStatusRequest: SpravkaStatusRequest) {
        viewModelScope.launch {
            val response = repository.getInstructorsList(spravkaStatusRequest)
            instructorsResponse.value = response
        }
    }

    var availableTimes = MutableLiveData<Response<AvailableTimesResponse>>()

    fun getAvailableTimes(availableTimesRequest: AvailableTimesRequest) {
        viewModelScope.launch {
            val response = repository.getAvailableTimes(availableTimesRequest)
            availableTimes.value = response
        }
    }

    var writingResponse = MutableLiveData<Response<StatusWithErrorResponse>>()

    fun writeToLesson(writeToLessonRequest: WriteToLessonRequest) {
        viewModelScope.launch {
            val response = repository.writeToLesson(writeToLessonRequest)
            writingResponse.value = response
        }
    }
}
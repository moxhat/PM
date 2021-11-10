package com.madcrew.pravamobil.view.fragment.progress.theorygroup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.GroupsRequest
import com.madcrew.pravamobil.models.responsemodels.GroupsResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class TheoryGroupViewModel  (private val repository: Repository): ViewModel() {

    var groupsResponse = MutableLiveData<Response<GroupsResponse>>()

    fun getGroupList(groupsRequest: GroupsRequest) {
        viewModelScope.launch {
            val response = repository.getGroupList(groupsRequest)
            groupsResponse.value = response
        }
    }
}
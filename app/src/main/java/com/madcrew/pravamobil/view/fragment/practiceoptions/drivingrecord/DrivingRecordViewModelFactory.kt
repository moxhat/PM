package com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository

class DrivingRecordViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DrivingRecordViewModel(repository) as T
    }
}
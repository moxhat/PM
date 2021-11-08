package com.madcrew.pravamobil.view.activity.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository

class ProgressViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProgressViewModel(repository) as T
    }
}
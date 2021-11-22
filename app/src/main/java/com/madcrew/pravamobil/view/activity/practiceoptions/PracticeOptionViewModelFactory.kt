package com.madcrew.pravamobil.view.activity.practiceoptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository

class PracticeOptionViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PracticeOptionViewModel(repository) as T
    }
}
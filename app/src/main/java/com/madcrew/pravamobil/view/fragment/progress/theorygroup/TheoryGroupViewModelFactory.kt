package com.madcrew.pravamobil.view.fragment.progress.theorygroup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository

class TheoryGroupViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TheoryGroupViewModel(repository) as T
    }
}
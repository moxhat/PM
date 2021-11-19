package com.madcrew.pravamobil.view.fragment.education.home.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.view.fragment.education.home.HomeViewModel

class HomePracticeViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomePracticeViewModel(repository) as T
    }
}
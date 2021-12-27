package com.madcrew.pravamobil.view.fragment.education.home.theory.before_theory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.view.fragment.education.home.HomeViewModel

class HomeBeforeTheoryViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeBeforeTheoryViewModel(repository) as T
    }
}
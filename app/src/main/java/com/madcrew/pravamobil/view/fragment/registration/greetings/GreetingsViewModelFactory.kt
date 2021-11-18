package com.madcrew.pravamobil.view.fragment.registration.greetings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.view.fragment.registration.enter.EnterViewModel

class GreetingsViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GreetingsViewModel(repository) as T
    }
}
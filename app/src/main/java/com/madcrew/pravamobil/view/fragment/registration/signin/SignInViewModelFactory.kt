package com.madcrew.pravamobil.view.fragment.registration.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.view.activity.progress.ProgressViewModel

class SignInViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignInViewModel(repository) as T
    }
}
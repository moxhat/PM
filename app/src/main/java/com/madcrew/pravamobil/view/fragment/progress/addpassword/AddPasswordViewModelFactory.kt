package com.madcrew.pravamobil.view.fragment.progress.addpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.view.fragment.registration.school.SchoolViewModel

class AddPasswordViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddPasswordViewModel(repository) as T
    }
}
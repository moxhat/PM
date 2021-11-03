package com.madcrew.pravamobil.view.fragment.registration.smscode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.view.fragment.registration.school.SchoolViewModel

class SmsCodeViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SmsCodeViewModel(repository) as T
    }
}
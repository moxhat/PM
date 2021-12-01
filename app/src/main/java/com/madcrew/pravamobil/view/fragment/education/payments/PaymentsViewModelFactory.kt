package com.madcrew.pravamobil.view.fragment.education.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionViewModel

class PaymentsViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PaymentsViewModel(repository) as T
    }
}
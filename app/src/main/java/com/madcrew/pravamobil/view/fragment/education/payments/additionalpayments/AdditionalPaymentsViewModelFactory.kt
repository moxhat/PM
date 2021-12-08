package com.madcrew.pravamobil.view.fragment.education.payments.additionalpayments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.view.fragment.education.payments.PaymentsViewModel

class AdditionalPaymentsViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AdditionalPaymentsViewModel(repository) as T
    }
}
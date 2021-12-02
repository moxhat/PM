package com.madcrew.pravamobil.view.activity.paymentsoptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionViewModel

class PaymentsOptionsViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PaymentsOptionsViewModel(repository) as T
    }
}
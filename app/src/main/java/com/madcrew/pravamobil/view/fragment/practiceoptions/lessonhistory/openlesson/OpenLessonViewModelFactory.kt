package com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.openlesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionViewModel

class OpenLessonViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OpenLessonViewModel(repository) as T
    }
}
package com.madcrew.pravamobil.view.fragment.progress.category

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.CategoryRequest
import com.madcrew.pravamobil.models.responsemodels.CategoryResponse
import com.shawnlin.numberpicker.NumberPicker
import kotlinx.coroutines.launch
import retrofit2.Response

class CategoryViewModel(private val repository: Repository): ViewModel() {

    var categoryResponse = MutableLiveData<Response<CategoryResponse>>()

    fun getCategoryList(categoryRequest: CategoryRequest) {
        viewModelScope.launch {
            val response = repository.getCategoryList(categoryRequest)
            categoryResponse.value = response
        }
    }

    fun setUpPicker(
        picker: NumberPicker,
        data: Array<String>
    ) {
        picker.apply {
            minValue = 0
            maxValue = data.size - 1
            displayedValues = data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                typeface = resources.getFont(R.font.ubuntu_m)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setSelectedTypeface(resources.getFont(R.font.ubuntu_m))
            }
            wrapSelectorWheel = true
        }

    }
}
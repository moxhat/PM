package com.madcrew.pravamobil.view.fragment.progress.filial

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.CategoryRequest
import com.madcrew.pravamobil.models.requestmodels.FilialRequest
import com.madcrew.pravamobil.models.responsemodels.CategoryResponse
import com.madcrew.pravamobil.models.responsemodels.FilialResponse
import com.shawnlin.numberpicker.NumberPicker
import kotlinx.coroutines.launch
import retrofit2.Response

class FilialViewModel (private val repository: Repository): ViewModel() {

    var filialResponse = MutableLiveData<Response<FilialResponse>>()

    fun getFilialList(filialRequest: FilialRequest) {
        viewModelScope.launch {
            val response = repository.getFilialList(filialRequest)
            filialResponse.value = response
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
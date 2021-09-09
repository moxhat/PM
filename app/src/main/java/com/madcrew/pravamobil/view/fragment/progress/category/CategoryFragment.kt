package com.madcrew.pravamobil.view.fragment.progress.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentCategoryBinding
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.view.fragment.progress.transmission.TransmissionFragment
import com.shawnlin.numberpicker.NumberPicker


class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager

        val picker = binding.categoryPicker
        val a = "\"A\""
        val b = "\"B\""
        val c = "\"C\""
        val d = "\"D\""
        val m = "\"M\""

        val data =
            arrayOf("Категория $a", "Категория $b", "Категория $c", "Категория $d", "Категория $m")

        setUpPicker(picker, data)

        binding.btCategoryNext.setOnClickListener {
            val selectedCategory = data[picker.value]
            nextFragmentInProgress(mainManager, TransmissionFragment(selectedCategory))
        }
    }

    private fun setUpPicker(
        picker: NumberPicker,
        data: Array<String>
    ) {
        picker.apply {
            minValue = 0
            maxValue = data.size - 1
            displayedValues = data
            typeface = resources.getFont(R.font.ubuntu_m)
            setSelectedTypeface(resources.getFont(R.font.ubuntu_m))
            wrapSelectorWheel = true
        }

    }
}
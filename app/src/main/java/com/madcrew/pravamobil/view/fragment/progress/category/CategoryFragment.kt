package com.madcrew.pravamobil.view.fragment.progress.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentCategoryBinding
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

        val picker = binding.categoryPicker
        val a = "\"A\""
        val b = "\"B\""
        val c = "\"C\""
        val d = "\"D\""
        val m = "\"M\""

        val data =
            arrayOf("Категория $a", "Категория $b", "Категория $c", "Категория $d", "Категория $m")

        setUpPicker(picker, data)

        picker.setOnClickListener {
            val selectedCategory = data[picker.value]
            val mainManager = parentFragmentManager
            val transaction: FragmentTransaction = mainManager.beginTransaction()
            transaction.replace(
                R.id.progress_activity_fragment_container,
                TransmissionFragment(selectedCategory)
            )
            transaction.commit()
        }
    }

    private fun setUpPicker(
        picker: NumberPicker,
        data: Array<String>
    ) {
        picker.minValue = 0
        picker.maxValue = data.size - 1
        picker.displayedValues = data
        picker.typeface = resources.getFont(R.font.ubuntu_m)
        picker.setSelectedTypeface(resources.getFont(R.font.ubuntu_m))
        picker.wrapSelectorWheel = true
    }
}
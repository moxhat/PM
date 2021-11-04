package com.madcrew.pravamobil.view.fragment.progress.filial

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentFilialBinding
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.view.fragment.progress.theorygroup.TheoryGroupFragment
import com.shawnlin.numberpicker.NumberPicker


class FilialFragment : Fragment() {

    private var _binding: FragmentFilialBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager

        val picker = binding.filialPicker

        val data =
            arrayOf("Центр", "Фрунзе", "Заволга", "Брагино", "Московский")

        setUpPicker(picker, data)

        binding.btFilialNext.setOnClickListener {
            val selectedCategory = data[picker.value]
            nextFragmentInProgress(mainManager, TheoryGroupFragment())
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
package com.madcrew.pravamobil.view.fragment.progress.passport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madcrew.pravamobil.databinding.FragmentPassportBinding
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.dateConverter
import com.madcrew.pravamobil.utils.dateConverterSpravka
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.view.fragment.progress.parentphone.ParentPhoneNumberFragment
import com.madcrew.pravamobil.view.fragment.progress.passportscan.PassportScanFragment


class PassportFragment(var type: String = "student") : Fragment() {

    private var _binding: FragmentPassportBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPassportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager

        binding.btPassportNext.setOnClickListener {
            when (type){
                "student" -> {
                    nextFragmentInProgress(mainManager, PassportScanFragment())
                    Preferences.setPrefsString("passportSeries", binding.passportSeriesText.text.toString(), requireContext())
                    Preferences.setPrefsString("passportNumber", binding.passportNumberText.text.toString(), requireContext())
                    Preferences.setPrefsString("passportGiver", binding.passportGiverText.text.toString(), requireContext())
                    Preferences.setPrefsString("passportDate", dateConverter(binding.passportGivenDateText.text.toString(), requireContext()), requireContext())
                    Preferences.setPrefsString("passportDepartmentCode", binding.passportDepartmentCodeText.text.toString(), requireContext())
                }
                "parent" -> nextFragmentInProgress(mainManager, ParentPhoneNumberFragment())
            }
        }
    }
}
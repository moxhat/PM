package com.madcrew.pravamobil.view.fragment.progress.checkdata

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentCheckDataBinding

import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.dateConverterSpravka


class CheckDataFragment : Fragment() {

    private var _binding: FragmentCheckDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentCheckDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val passportSeries = Preferences.getPrefsString("passportSeries",  requireContext())
        val passportNumber =Preferences.getPrefsString("passportNumber", requireContext())
        val passportGiver = Preferences.getPrefsString("passportGiver", requireContext())
        val passportDate = Preferences.getPrefsString("passportDate", requireContext())
        val passportDepartmentCode = Preferences.getPrefsString("passportDepartmentCode", requireContext())
        val birthDate = Preferences.getPrefsString("birthDate", requireContext())
        val snilsNumber = Preferences.getPrefsString("snils", requireContext())
        val transmission = Preferences.getPrefsString("transmission", requireContext())
        val theory = Preferences.getPrefsString("theory", requireContext())
        val registrationAddress = Preferences.getPrefsString("registrationAddress", requireContext())

        binding.checkDataDocumentText.text = "${resources.getString(R.string.series)} $passportSeries ${resources.getString(R.string.number)} $passportNumber, ${resources.getString(R.string.given)} $passportDate г., $passportGiver, ${resources.getString(R.string.department_code)} $passportDepartmentCode"
        binding.checkDataBirthdateText.text = birthDate
        binding.checkDataSnilsText.text = snilsNumber
        binding.checkDataTransmissionText.text = transmission
        binding.checkDataGroupText.text = theory
        binding.checkDataAddressText.text = registrationAddress

    }

}
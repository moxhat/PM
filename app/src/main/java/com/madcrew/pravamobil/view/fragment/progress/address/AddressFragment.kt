package com.madcrew.pravamobil.view.fragment.progress.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentAddressBinding
import com.madcrew.pravamobil.databinding.FragmentSnilsBinding
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.view.fragment.progress.passportscan.PassportScanFragment


class AddressFragment : Fragment() {

    private var _binding: FragmentAddressBinding? = null
    private val binding get() = _binding!!
    private var checkAddress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainProgress = parentFragmentManager

        binding.addressCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            checkAddress = isChecked
        }

        binding.btAddressNext.setOnClickListener {
            Preferences.setPrefsString(
                "registrationAddress",
                "${binding.addressRegionText.text}, " +
                        "${binding.addressCityText.text}, " +
                        "${binding.addressStreetText.text}, " +
                        "${binding.addressHouseText.text}, " +
                        "${binding.addressHousingText.text}, " +
                        "${binding.addressApartmentText.text}",
                requireContext()
            )
            if (checkAddress) {
                nextFragmentInProgress(
                    mainProgress,
                    PassportScanFragment(R.string.registration_scan_title, "registrationAddress")
                )
            } else {
                livingAddress()
            }
        }
    }

    private fun livingAddress() {
        binding.addressRegionText.text?.clear()
        binding.addressCityText.text?.clear()
        binding.addressStreetText.text?.clear()
        binding.addressHouseText.text?.clear()
        binding.addressHousingText.text?.clear()
        binding.addressApartmentText.text?.clear()
        binding.addressTitle.setText(R.string.living_address)
        binding.addressCheck.setGone()
        binding.addressRegionText.requestFocus()
        binding.addressRegionText.clearFocus()
        checkAddress = true
    }
}
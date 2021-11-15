package com.madcrew.pravamobil.view.fragment.progress.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentAddressBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.submodels.AddressModel
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.passportscan.PassportScanFragment


class AddressFragment : Fragment() {

    private var _binding: FragmentAddressBinding? = null
    private val binding get() = _binding!!
    private var checkAddress = false
    private var region = ""
    private var city = ""
    private var street = ""
    private var house = ""
    private var housing = ""
    private var apartment = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parent = this.context as ProgressActivity

        parent.updateProgress("RegisterAddressPage")

        binding.addressCheck.setOnCheckedChangeListener { _, isChecked ->
            checkAddress = isChecked
        }

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        val regionText = binding.addressRegionText
        val regionField = binding.addressRegion
        val cityText = binding.addressCityText
        val cityField = binding.addressCity
        val streetText = binding.addressStreetText
        val streetField = binding.addressStreet
        val houseText = binding.addressHouseText
        val houseField = binding.addressHouse
        val apartmentText = binding.addressApartmentText
        val apartmentField = binding.addressApartment

        regionText.doOnTextChanged { _, _, _, _ ->
            if (regionText.length() > 1) regionField.setErrorOff()
        }

        cityText.doOnTextChanged { _, _, _, _ ->
            if (cityText.length() > 1) cityField.setErrorOff()
        }

        streetText.doOnTextChanged { _, _, _, _ ->
            if (streetText.length() > 1) streetField.setErrorOff()
        }

        houseText.doOnTextChanged { _, _, _, _ ->
            if (houseText.length() > 1) houseField.setErrorOff()
        }

        apartmentText.doOnTextChanged { _, _, _, _ ->
            if (apartmentText.length() > 1) apartmentField.setErrorOff()
        }

        binding.btAddressNext.setOnClickListener {
            if (regionText.length() > 2 && cityText.length() > 2 && streetText.length() > 2 && houseText.length() != 0 && apartmentText.length() != 0) {
                if (binding.addressTitle.text == resources.getString(R.string.registration_address)) {
                if (checkAddress) {
                    region = binding.addressRegionText.text.toString()
                    city = binding.addressCityText.text.toString()
                    street = binding.addressStreetText.text.toString()
                    house = binding.addressHouseText.text.toString()
                    housing = binding.addressHousingText.text.toString()
                    apartment = binding.addressApartmentText.text.toString()
                    if (isOnline(requireContext())){
                        parent.updateClientData(FullRegistrationRequest(TOKEN, clientId, schoolId, address = AddressModel(region, city, street, house, housing, apartment), livingAddress = AddressModel(region, city, street, house, housing, apartment)))
                        nextFragmentInProgress(
                            parentFragmentManager,
                            PassportScanFragment(
                                R.string.registration_scan_title,
                                "registrationAddress"
                            )
                        )
                    } else {
                        noInternet(requireContext())
                    }
                } else {
                    livingAddress()
                }
                } else {
                    val lRegion = binding.addressRegionText.text.toString()
                    val lCity = binding.addressCityText.text.toString()
                    val lStreet = binding.addressStreetText.text.toString()
                    val lHouse = binding.addressHouseText.text.toString()
                    val lHousing = binding.addressHousingText.text.toString()
                    val lApartment = binding.addressApartmentText.text.toString()
                    if (isOnline(requireContext())){
                        parent.updateClientData(FullRegistrationRequest(TOKEN, clientId, schoolId, address = AddressModel(region, city, street, house, housing, apartment), livingAddress = AddressModel(lRegion, lCity, lStreet, lHouse, lHousing, lApartment)))
                        nextFragmentInProgress(
                            parentFragmentManager,
                            PassportScanFragment(
                                R.string.registration_scan_title,
                                "registrationAddress"
                            )
                        )
                    } else {
                        noInternet(requireContext())
                    }
                }
            } else {
                if (regionText.length() < 2) regionField.setErrorOn()
                if (cityText.length() < 2) cityField.setErrorOn()
                if (streetText.length() < 2) streetField.setErrorOn()
                if (houseText.length() == 0) houseField.setErrorOn()
                if (apartmentText.length() == 0) apartmentField.setErrorOn()
            }
        }
    }

    private fun livingAddress() {
        region = binding.addressRegionText.text.toString()
        city = binding.addressCityText.text.toString()
        street = binding.addressStreetText.text.toString()
        house = binding.addressHouseText.text.toString()
        housing = binding.addressHousingText.text.toString()
        apartment = binding.addressApartmentText.text.toString()
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
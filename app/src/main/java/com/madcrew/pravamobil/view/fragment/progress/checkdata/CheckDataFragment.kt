package com.madcrew.pravamobil.view.fragment.progress.checkdata

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentCheckDataBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.ClientInfoRequest
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.utils.previousFragmentInProgress
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.activity.progress.ProgressViewModel
import com.madcrew.pravamobil.view.activity.progress.ProgressViewModelFactory
import com.madcrew.pravamobil.view.fragment.progress.address.AddressFragment
import com.madcrew.pravamobil.view.fragment.progress.confirmcontract.ConfirmContractFragment
import com.madcrew.pravamobil.view.fragment.progress.passport.PassportFragment
import com.madcrew.pravamobil.view.fragment.progress.snils.SnilsFragment
import com.madcrew.pravamobil.view.fragment.progress.studentname.StudentNameFragment
import com.madcrew.pravamobil.view.fragment.progress.theory.SelectTheoryFragment


class CheckDataFragment(var type: String = "student") : Fragment() {

    private var _binding: FragmentCheckDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parent = this.context as ProgressActivity

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        Preferences.setPrefsString("checkData", "true", requireContext())

        parent.updateProgress("CheckDataPage")
        parent.mViewModel.getClientInfo(
            ClientInfoRequest(
                TOKEN,
                schoolId,
                clientId,
                cells = listOf("dateBirthday", "passport", "snils", "kpp", "format", "place")
            )
        )



        parent.mViewModel.clientInfo.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                if (response.body()!!.status == "done") {
                    val passportSeries = response.body()!!.client.passport?.series
                    val passportNumber = response.body()!!.client.passport?.number
                    val passportGiver = response.body()!!.client.passport?.office
                    val passportDate = response.body()!!.client.passport?.date
                    val passportDepartmentCode = response.body()!!.client.passport?.code
                    val birthDate = response.body()!!.client.dateBirthday
                    val snilsNumber = response.body()!!.client.snils
                    val transmission = when (response.body()!!.client.kpp) {
                        "mechanic" -> resources.getString(R.string.mechanic)
                        "automatic" -> resources.getString(R.string.automatic)
                        else -> "empty"
                    }
                    val theory = when (response.body()!!.client.format) {
                        "FullTime" -> resources.getString(R.string.theory_offline)
                        else -> resources.getString(R.string.theory_online)
                    }
                    val housing = if (response.body()!!.client.place?.building != "") {
                        ", ${resources.getString(R.string.housing)} ${response.body()!!.client.place?.building}"
                    } else {
                        ""
                    }
                    val registrationAddress =
                        "${response.body()!!.client.place?.region.toString()} обл., г. ${response.body()!!.client.place?.city}, ул. ${response.body()!!.client.place?.street}, ${
                            resources.getString(R.string.house)
                        } ${response.body()!!.client.place?.home}$housing, ${resources.getString(R.string.apartment)} ${response.body()!!.client.place?.apartment}"
                    binding.checkDataDocumentText.text =
                        "${resources.getString(R.string.series)} $passportSeries ${
                            resources.getString(R.string.number)
                        } $passportNumber, ${resources.getString(R.string.given)} $passportDate г., $passportGiver, ${
                            resources.getString(
                                R.string.department_code
                            )
                        } $passportDepartmentCode"
                    binding.checkDataBirthdateText.text = birthDate
                    binding.checkDataSnilsText.text = snilsNumber
                    binding.checkDataTransmissionText.text = transmission
                    binding.checkDataGroupText.text = theory
                    binding.checkDataAddressText.text = registrationAddress
                }
            }
        })

        binding.btCheckDataDocumentEdit.setOnClickListener {
            previousFragmentInProgress(parentFragmentManager, PassportFragment("student"))
        }

        binding.btCheckDataBirthdateEdit.setOnClickListener {
            previousFragmentInProgress(parentFragmentManager, StudentNameFragment(type = "student"))
        }

        binding.btCheckDataSnilsEdit.setOnClickListener {
            previousFragmentInProgress(parentFragmentManager, SnilsFragment())
        }

        binding.btCheckDataAddressEdit.setOnClickListener {
            previousFragmentInProgress(parentFragmentManager, AddressFragment())
        }

        binding.btCheckDataGroupEdit.setOnClickListener {
            previousFragmentInProgress(parentFragmentManager, SelectTheoryFragment())
        }

        binding.btCheckDataNext.setOnClickListener {
            nextFragmentInProgress(parentFragmentManager, ConfirmContractFragment(type))
        }


    }

}
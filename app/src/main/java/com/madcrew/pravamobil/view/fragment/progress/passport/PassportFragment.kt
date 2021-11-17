package com.madcrew.pravamobil.view.fragment.progress.passport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentPassportBinding
import com.madcrew.pravamobil.domain.BaseUrl
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.requestmodels.ClientInfoRequest
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.requestmodels.ProgressRequest
import com.madcrew.pravamobil.models.submodels.ParentModel
import com.madcrew.pravamobil.models.submodels.PassportModel
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.checkdata.CheckDataFragment
import com.madcrew.pravamobil.view.fragment.progress.parentphone.ParentPhoneNumberFragment
import com.madcrew.pravamobil.view.fragment.progress.passportscan.PassportScanFragment


class PassportFragment(var type: String = "student", var title: Int  = R.string.of_student) : Fragment() {

    private var _binding: FragmentPassportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPassportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seriesText = binding.passportSeriesText
        val seriesField = binding.passportSeries
        val numberText = binding.passportNumberText
        val numberField = binding.passportNumber
        val giverText = binding.passportGiverText
        val giverField = binding.passportGiver
        val dateText = binding.passportGivenDateText
        val dateField = binding.passportGivenDate
        val departmentText = binding.passportDepartmentCodeText
        val departmentField = binding.passportDepartmentCode

        binding.passportOf.setText(title)

        val parent = this.context as ProgressActivity

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
        val checkData = Preferences.getPrefsString("checkData",  requireContext()) == "true"

        if (checkData){
            parent.getClientInfo(ClientInfoRequest(TOKEN, schoolId, clientId, listOf("dateBirthday", "passport", "snils", "kpp", "format", "place")))
            parent.mViewModel.clientInfo.observe(viewLifecycleOwner, {response ->
                if (response.isSuccessful){
                    if (response.body()!!.status == "done" && response.body()!!.client.passport != null){
                        val series = response.body()!!.client.passport?.series.toString()
                        val number = response.body()!!.client.passport?.number.toString()
                        val giver = response.body()!!.client.passport?.office.toString()
                        val date = response.body()!!.client.passport?.date.toString()
                        val department = response.body()!!.client.passport?.code.toString()
                        setData(series, number, giver, date, department)
                    }
                }
            })
        } else {
            if (type == "student"){
                parent.updateProgress("RegisterPassportPage")
            } else {
                parent.updateProgress("RegisterParentPassportPage")
            }
        }


        seriesText.doOnTextChanged{_,_,_,_ ->
            if(seriesText.length() > 1) seriesField.setErrorOff()
            if (seriesText.length() == 4) numberText.requestFocus()
        }

        numberText.doOnTextChanged{_,_,_,_ ->
            if(numberText.length() > 1) numberField.setErrorOff()
            if (numberText.length() == 6) giverText.requestFocus()
        }

        giverText.doOnTextChanged{_,_,_,_ ->
            if(giverText.length() > 1) giverField.setErrorOff()
        }

        dateText.doOnTextChanged{_,_,_,_ ->
            if(dateText.length() > 1) dateField.setErrorOff()
            if (dateText.length() == 10) departmentText.requestFocus()
        }

        departmentText.doOnTextChanged{_,_,_,_ ->
            if(departmentText.length() > 1) departmentField.setErrorOff()
            if (departmentText.length() == 7) departmentText.hideKeyboard()
        }

        binding.btPassportNext.setOnClickListener {
            if (seriesText.length() == 4 && numberText.length() == 6 && giverText.length() > 2 && dateText.length() == 10 && departmentText.length() == 7){
                val passportSeries = binding.passportSeriesText.text.toString()
                val passportNumber = binding.passportNumberText.text.toString()
                val passportGiver = binding.passportGiverText.text.toString()
                val passportDate = binding.passportGivenDateText.text.toString()
                val passportDepartmentCode = binding.passportDepartmentCodeText.text.toString()
                when (type){
                    "student" -> {
                        parent.updateClientData(FullRegistrationRequest(TOKEN, clientId, schoolId, passport = PassportModel(passportSeries, passportNumber, passportGiver, passportDate, passportDepartmentCode)))
                        if (checkData){
                            nextFragmentInProgress(parentFragmentManager, CheckDataFragment("student"))
                        } else {
                            nextFragmentInProgress(parentFragmentManager, PassportScanFragment())
                        }
                    }
                    "parent" -> {
                        parent.updateClientData(FullRegistrationRequest(TOKEN, clientId, schoolId, parent = ParentModel(passport = PassportModel(passportSeries, passportNumber, passportGiver,passportDate, passportDepartmentCode))))
                        nextFragmentInProgress(parentFragmentManager, ParentPhoneNumberFragment())
                    }
                }
            } else {
                if (seriesText.length() < 4) seriesField.setErrorOn()
                if (numberText.length() < 6) numberField.setErrorOn()
                if (giverText.length() < 2) giverField.setErrorOn()
                if (dateText.length() < 10) dateField.setErrorOn()
                if (departmentText.length() < 7) departmentField.setErrorOn()
            }
        }
    }

    private fun setData(series: String, number: String, giver: String, date: String, department: String){
        binding.passportSeriesText.setText(series)
        binding.passportNumberText.setText(number)
        binding.passportGiverText.setText(giver)
        binding.passportGivenDateText.setText(date)
        binding.passportDepartmentCodeText.setText(department)
    }
}
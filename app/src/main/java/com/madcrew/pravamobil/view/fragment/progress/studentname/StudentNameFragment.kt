package com.madcrew.pravamobil.view.fragment.progress.studentname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentStudentNameBinding
import com.madcrew.pravamobil.domain.BaseUrl
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.requestmodels.ProgressRequest
import com.madcrew.pravamobil.models.submodels.ParentModel
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.documenttype.DocumentTypeFragment
import com.madcrew.pravamobil.view.fragment.progress.passport.PassportFragment
import android.graphics.Bitmap

import android.graphics.BitmapFactory
import com.madcrew.pravamobil.models.requestmodels.ClientInfoRequest
import com.madcrew.pravamobil.view.fragment.progress.checkdata.CheckDataFragment


class StudentNameFragment(var title: Int = R.string.student, var type: String = "student") : Fragment() {

    private var _binding: FragmentStudentNameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.studentNameTitle.setText(title)

        val secondNameText = binding.studentNameSecondNameText
        val secondNameField = binding.studentNameSecondName
        val firstNameText = binding.studentNameFirstText
        val firstNameField = binding.studentNameFirstName
        val thirdNameText = binding.studentNameThirdText
        val thirdNameField = binding.studentNameThirdName
        val birthDateText = binding.studentNameBirthDateText
        val birthDateField = binding.studentNameBirthDate

        val parent = this.context as ProgressActivity

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        val checkData = Preferences.getPrefsString("checkData",  requireContext()) == "true"

        if (checkData){
            parent.getClientInfo(ClientInfoRequest(TOKEN, schoolId, clientId, listOf("lastName", "name", "patronymic", "dateBirthday", "passport", "snils", "kpp", "format", "place")))
            parent.mViewModel.clientInfo.observe(viewLifecycleOwner, {response ->
                if (response.isSuccessful){
                    if (response.body()!!.status == "done" && response.body()!!.client.name != null){
                        val name = response.body()!!.client.name.toString()
                        val secondName = response.body()!!.client.lastName.toString()
                        val thirdName = response.body()!!.client.patronymic.toString()
                        val date = response.body()!!.client.dateBirthday.toString()
                        setData(name, secondName, thirdName, date)
                    }
                }
            })
        } else {
            if (type == "student") {
                parent.mViewModel.updateProgress(
                    ProgressRequest(
                        BaseUrl.TOKEN,
                        schoolId,
                        clientId,
                        "RegisterPersonalDataPage"
                    )
                )
            } else {
                parent.mViewModel.updateProgress(
                    ProgressRequest(
                        BaseUrl.TOKEN,
                        schoolId,
                        clientId,
                        "RegisterParentDataPage"
                    )
                )
            }
        }


        secondNameText.doOnTextChanged{_,_,_,_ ->
            if(secondNameText.length() > 1) secondNameField.setErrorOff()
        }

        firstNameText.doOnTextChanged{_,_,_,_ ->
            if(firstNameText.length() > 1) firstNameField.setErrorOff()
        }

        thirdNameText.doOnTextChanged{_,_,_,_ ->
            if(thirdNameText.length() > 1) thirdNameField.setErrorOff()
        }

        birthDateText.doOnTextChanged{_,_,_,_ ->
            if(birthDateText.length() > 1) birthDateField.setErrorOff()
            if (birthDateText.length() == 10) this.view?.hideKeyboard()
        }

        binding.btStudentNameNext.setOnClickListener {
            if (secondNameText.length() > 2 && firstNameText.length() > 2 && thirdNameText.length() > 2 && birthDateText.length() == 10){
                val lastname = secondNameText.text.toString().replaceFirstChar { it.uppercase() }
                val name = firstNameText.text.toString().replaceFirstChar { it.uppercase() }
                val thirdName = thirdNameText.text.toString().replaceFirstChar { it.uppercase() }
                val birthDate = birthDateText.text.toString()
                when(type){
                    "student" -> {
                        Preferences.setPrefsString("birthDate", dateConverter(binding.studentNameBirthDateText.text.toString(), requireContext()), requireContext())
                        parent.updateClientData(FullRegistrationRequest(TOKEN, clientId, schoolId, lastName = lastname, name = name, patronymic = thirdName, dateBirthday = birthDate))
                        if (checkData){
                            nextFragmentInProgress(parentFragmentManager, CheckDataFragment("student"))
                        } else {
                            nextFragmentInProgress(parentFragmentManager, PassportFragment())
                        }
                    }
                    "parent" -> {
                        parent.updateClientData(FullRegistrationRequest(TOKEN, clientId, schoolId, parent = ParentModel(lastName = lastname, name = name, thirdName = thirdName, dateBirthday = birthDate)))
                        nextFragmentInProgress(
                            parentFragmentManager,
                            DocumentTypeFragment(title2 = R.string.representatives, "parent")
                        )
                    }
                }
            } else {
                if (secondNameText.length() < 2) secondNameField.setErrorOn()
                if (firstNameText.length() < 2) firstNameField.setErrorOn()
                if (thirdNameText.length() < 2) thirdNameField.setErrorOn()
                if (birthDateText.length() < 10) birthDateField.setErrorOn()
            }
        }
    }
    private fun setData(firstName: String, secondName: String, thirdName: String, date: String){
        binding.studentNameFirstText.setText(firstName)
        binding.studentNameSecondNameText.setText(secondName)
        binding.studentNameThirdText.setText(thirdName)
        binding.studentNameBirthDateText.setText(date)
    }
}
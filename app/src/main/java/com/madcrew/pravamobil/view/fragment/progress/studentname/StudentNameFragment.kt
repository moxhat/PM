package com.madcrew.pravamobil.view.fragment.progress.studentname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentStudentNameBinding
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.fragment.progress.documenttype.DocumentTypeFragment
import com.madcrew.pravamobil.view.fragment.progress.passport.PassportFragment

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

        val nameText = binding.studentNameSecondNameText
        val nameField = binding.studentNameSecondName
        val firstNameText = binding.studentNameFirstText
        val firstNameField = binding.studentNameFirstName
        val thirdNameText = binding.studentNameThirdText
        val thirdNameField = binding.studentNameThirdName
        val birthDateText = binding.studentNameBirthDateText
        val birthDateField = binding.studentNameBirthDate

        nameText.doOnTextChanged{_,_,_,_ ->
            if(nameText.length() > 1) nameField.setErrorOff()
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
            if (nameText.length() > 2 && firstNameText.length() > 2 && thirdNameText.length() > 2 && birthDateText.length() == 10){
                when(type){
                    "student" -> {
                        nextFragmentInProgress(parentFragmentManager, PassportFragment())
                        Preferences.setPrefsString("birthDate", dateConverter(binding.studentNameBirthDateText.text.toString(), requireContext()), requireContext())
                    }
                    "parent" ->  nextFragmentInProgress(parentFragmentManager, DocumentTypeFragment(R.string.representatives, "parent"))
                }
            } else {
                if (nameText.length() < 2) nameField.setErrorOn()
                if (firstNameText.length() < 2) firstNameField.setErrorOn()
                if (thirdNameText.length() < 2) thirdNameField.setErrorOn()
                if (birthDateText.length() < 10) birthDateField.setErrorOn()
            }
        }
    }
}
package com.madcrew.pravamobil.view.fragment.progress.parentphone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentParentPhoneNumberBinding
import com.madcrew.pravamobil.databinding.FragmentPassportBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.submodels.ParentModel
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.checkdata.CheckDataFragment


class ParentPhoneNumberFragment : Fragment() {

    private var _binding: FragmentParentPhoneNumberBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParentPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parent = this.context as ProgressActivity
        val phoneText = binding.parentPhoneEditText
        val phoneField = binding.parentPhonePhone

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        parent.updateProgress("RegisterParentPhonePage")

        phoneText.doOnTextChanged{_,_,_,_ ->
            if(phoneText.length() > 1) phoneField.setErrorOff()
            if (phoneText.length() == 16) this.view?.hideKeyboard()
        }

        binding.btParentPhoneNext.setOnClickListener {
            if (phoneText.length() == 16){
                val parentPhone = phoneText.text.toString()
                if (isOnline(requireContext())){
                    parent.updateClientData(FullRegistrationRequest(TOKEN, clientId, schoolId, parent = ParentModel(phoneNumber = parentPhone)))
                    nextFragmentInProgress(parentFragmentManager, CheckDataFragment("parent"))
                } else {
                    noInternet(requireContext())
                }
            } else {
                phoneField.setErrorOn()
            }
        }
    }
}
package com.madcrew.pravamobil.view.fragment.progress.addpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentAddPasswordBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.AddPasswordRequest
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.fragment.progress.email.EmailFragment


class AddPasswordFragment : Fragment() {

    private var _binding: FragmentAddPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager
        val repository = Repository()
        val viewModelFactory = AddPasswordViewModelFactory(repository)
        val mViewModel = ViewModelProvider(this, viewModelFactory).get(AddPasswordViewModel::class.java)

        val firstPassword = binding.addPasswordPassword2
        val secondPassword = binding.addPasswordPassword1
        val firstPasswordText = binding.addPasswordEditText2
        val secondPasswordText = binding.addPasswordEditText1

        mViewModel.firstRegistrationResponse.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                if (response.body()!!.status == "done"){
                    nextFragmentInProgress(mainManager, EmailFragment())
                }
            }
        })

        firstPasswordText.doOnTextChanged { _, _, _, _ ->
            if (firstPasswordText.length() == 8) {
                secondPasswordText.requestFocus()
            }
            if (firstPasswordText.length() > 1) {
                secondPassword.isErrorEnabled = false
                firstPassword.isErrorEnabled = false
            }
        }

        secondPasswordText.doOnTextChanged { _, _, _, _ ->
            if (secondPasswordText.length() == 8) {
                this.view?.hideKeyboard()
            }
            if (secondPasswordText.length() > 1) {
                secondPassword.isErrorEnabled = false
                firstPassword.isErrorEnabled = false
            }
        }

        binding.btAddPasswordNext.setOnClickListener {
            chekPassword(
                firstPasswordText,
                secondPasswordText,
                secondPassword,
                firstPassword,
                mViewModel
            )
        }
    }

    private fun chekPassword(
        firstPasswordText: TextInputEditText,
        secondPasswordText: TextInputEditText,
        secondPassword: TextInputLayout,
        firstPassword: TextInputLayout,
        viewModel: AddPasswordViewModel
    ) {
        if (firstPasswordText.length() == 8 && secondPasswordText.length() == 8) {
            if (firstPasswordText.text.toString() == secondPasswordText.text.toString()) {
                if (isOnline(requireContext())){
                    val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
                    val firstName = Preferences.getPrefsString("firstName", requireContext()).toString()
                    val phoneNumber = Preferences.getPrefsString("phoneNumber", requireContext()).toString()
                    viewModel.addPassword(AddPasswordRequest(TOKEN, schoolId, firstName, phoneNumber, firstPasswordText.text.toString()))
                } else {
                    noInternet(requireContext())
                }
            } else {
                secondPassword.apply {
                    isErrorEnabled = true
                    error = resources.getString(R.string.passwords_mismatch)
                }
                firstPassword.apply {
                    isErrorEnabled = true
                    error = ""
                }
            }
        } else {
            secondPassword.apply {
                error = resources.getString(R.string.add_password_help)
                isErrorEnabled = true
            }
            firstPassword.apply {
                isErrorEnabled = true
                error = ""
            }
        }
    }
}
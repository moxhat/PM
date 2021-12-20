package com.madcrew.pravamobil.view.fragment.progress.addpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        firstPasswordText.requestFocus()

        mViewModel.firstRegistrationResponse.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                when (response.body()!!.status){
                    "done" -> {
                        Preferences.setPrefsString("rememberMe", "true", requireContext())
                        Preferences.setPrefsString("password", firstPasswordText.text.toString(), requireContext())
                        Preferences.setPrefsString("clientId", response.body()!!.clientId, requireContext())
                        nextFragmentInProgress(mainManager, EmailFragment())
                    }
                    "exist" -> {
                        Toast.makeText(requireContext(), resources.getString(R.string.number_exist), Toast.LENGTH_SHORT).show()
                    }
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
           if ( chekPassword(
                firstPasswordText,
                secondPasswordText,
                secondPassword,
                firstPassword,
                mViewModel
            )) {
               if (isOnline(requireContext())){
                   val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
                   val firstName = Preferences.getPrefsString("firstName", requireContext()).toString()
                   val phoneNumber = Preferences.getPrefsString("phoneNumber", requireContext()).toString()
                   mViewModel.addPassword(AddPasswordRequest(TOKEN, schoolId, firstName, phoneNumber, firstPasswordText.text.toString()))
               } else {
                   noInternet(requireContext())
               }
           }
        }
    }

    private fun chekPassword(
        firstPasswordText: TextInputEditText,
        secondPasswordText: TextInputEditText,
        secondPassword: TextInputLayout,
        firstPassword: TextInputLayout,
        viewModel: AddPasswordViewModel
    ): Boolean {
        if (firstPasswordText.length() == 8 && secondPasswordText.length() == 8) {
            return if (firstPasswordText.text.toString() == secondPasswordText.text.toString()) {
                true
            } else {
                secondPassword.apply {
                    isErrorEnabled = true
                    error = resources.getString(R.string.passwords_mismatch)
                }
                firstPassword.apply {
                    isErrorEnabled = true
                    error = ""
                }
                false
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
            return false
        }
    }
}
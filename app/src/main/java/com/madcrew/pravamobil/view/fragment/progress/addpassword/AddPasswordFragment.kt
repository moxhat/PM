package com.madcrew.pravamobil.view.fragment.progress.addpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentAddPasswordBinding
import com.madcrew.pravamobil.utils.hideKeyboard
import com.madcrew.pravamobil.utils.nextFragmentInProgress
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

        val firstPassword = binding.addPasswordPassword2
        val secondPassword = binding.addPasswordPassword1
        val firstPasswordText = binding.addPasswordEditText2
        val secondPasswordText = binding.addPasswordEditText1

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
                mainManager,
                EmailFragment()
            )
//            nextFragmentInProgress(mainManager, EmailFragment())
        }
    }

    private fun chekPassword(
        firstPasswordText: TextInputEditText,
        secondPasswordText: TextInputEditText,
        secondPassword: TextInputLayout,
        firstPassword: TextInputLayout,
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        if (firstPasswordText.length() == 8 && secondPasswordText.length() == 8) {
            if (firstPasswordText.text.toString() == secondPasswordText.text.toString()) {
                nextFragmentInProgress(fragmentManager, fragment)
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
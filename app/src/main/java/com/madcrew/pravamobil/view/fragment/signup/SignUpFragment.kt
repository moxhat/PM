package com.madcrew.pravamobil.view.fragment.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputLayout
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSignUpBinding
import com.madcrew.pravamobil.utils.hideKeyboard
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.utils.setInvisible
import com.madcrew.pravamobil.view.fragment.EnterFragment


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager

        val noTextAlert = binding.signupNotextAlert
        val btGetCode = binding.btSignupCode
        val nameText = binding.signupNameEditText
        val phoneText = binding.signupPhoneEditText

        noTextAlert.setInvisible()
        btGetCode.alpha = 0.5f


        phoneText.doOnTextChanged { text, start, before, count ->
            if (count == 12) {
                this.view?.hideKeyboard()
            }
            if (phoneText.length() == 12 && nameText.length() > 2) {
                btGetCode.alpha = 1f
            } else {
                btGetCode.alpha = 0.5f
            }
            if (phoneText.length() >= 10) {
                binding.signupPhone.setErrorOff()
            }
        }

        nameText.doOnTextChanged { text, start, before, count ->
            if (phoneText.length() == 12 && nameText.length() > 2) {
                btGetCode.alpha = 1f
            } else {
                btGetCode.alpha = 0.5f
            }
            if (nameText.length() >= 2) {
                binding.signupName.setErrorOff()
            }
        }

        binding.btSignupCode.setOnClickListener {
            if (nameText.length() < 2) {
                binding.signupName.setErrorOn()
            }
            if (phoneText.length() < 12) {
                binding.signupPhone.setErrorOn()
            }
        }

        binding.btSignupBack.setOnClickListener {
            val transaction: FragmentTransaction = mainManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out)
            transaction.replace(R.id.enter_activity_fragment_container, EnterFragment())
            transaction.commit()
        }
    }

    private fun TextInputLayout.setErrorOn() {
        this.isErrorEnabled = true
        this.error = resources.getString(R.string.name_alert)
    }

    private fun TextInputLayout.setErrorOff() {
        this.error = null
        this.isErrorEnabled = false
    }

}
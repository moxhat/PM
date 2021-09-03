package com.madcrew.pravamobil.view.fragment.registration.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputLayout
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSignUpBinding
import com.madcrew.pravamobil.utils.MaskWatcher
import com.madcrew.pravamobil.utils.hideKeyboard
import com.madcrew.pravamobil.utils.setInvisible
import com.madcrew.pravamobil.view.fragment.registration.EnterFragment
import com.madcrew.pravamobil.view.fragment.registration.smscode.SmsCodeFragment


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btGetCode = binding.btSignupCode
        val nameText = binding.signupNameEditText
        val phoneText = binding.signupPhoneEditText

        val nameField = binding.signupName
        val phoneField = binding.signupPhone

        phoneText.doOnTextChanged { _, _, _, _ ->
            if (phoneText.length() > 3) phoneField.setErrorOff()
        }

        nameText.doOnTextChanged { _, _, _, _ ->
            if (nameText.length() > 1) nameField.setErrorOff()
        }

        btGetCode.setOnClickListener {
            if (nameText.length() > 1 && phoneText.length() == 16){
                replaceFragment(SmsCodeFragment(), R.anim.fade_in, R.anim.fade_out)
            } else {
                if (phoneText.length() < 16) phoneField.setErrorOn()
                if (nameText.length() < 2) nameField.setErrorOn()
            }
        }

        binding.btSignupBack.setOnClickListener {
            replaceFragment(EnterFragment(),R.anim.slide_right_in, R.anim.slide_right_out)
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

    private fun replaceFragment(fragment: Fragment, animationIn: Int, animationOut: Int){
        val mainManager = parentFragmentManager
        val transaction: FragmentTransaction = mainManager.beginTransaction()
        transaction.setCustomAnimations(animationIn, animationOut)
        transaction.remove(this)
        transaction.replace(R.id.enter_activity_fragment_container, fragment)
        transaction.commit()
    }
}
package com.madcrew.pravamobil.view.fragment.registration.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputLayout
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSignInBinding
import com.madcrew.pravamobil.utils.hideKeyboard
import com.madcrew.pravamobil.view.fragment.registration.EnterFragment
import com.madcrew.pravamobil.view.fragment.registration.greetings.GreetingsFragment


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager
        val loginText = binding.signinLoginEditText
        val loginField = binding.signinLogin
        val passwordText = binding.signinPasswordEditText
        val passwordField = binding.signinPassword

        val tempPassword = "00000000"

        loginText.doOnTextChanged { _, _, _, _ ->
            if (loginText.length() > 1) loginField.setErrorOff()
            if (loginText.length() == 16) passwordText.requestFocus()
        }

        passwordText.doOnTextChanged { _, _, _, _ ->
            if (passwordText.length() > 1) passwordField.setErrorOff()
            if (passwordText.length() == 8) this.view?.hideKeyboard()
        }

        binding.btSigninBack.setOnClickListener {
            previousFragment(mainManager, EnterFragment())
        }

        binding.btSigninEnter.setOnClickListener {
            if (loginText.length() < 16) loginField.setErrorOn()
            if (passwordText.text.toString() == tempPassword){
                nextFragment(mainManager, GreetingsFragment())
            } else {
                passwordField.isErrorEnabled = true
                passwordField.error = resources.getString(R.string.wrong_password)
            }
        }
    }

    private fun previousFragment(mainManager: FragmentManager, fragment: Fragment) {
        val transaction: FragmentTransaction = mainManager.beginTransaction()
        transaction.apply {
            setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out)
            replace(R.id.enter_activity_fragment_container, fragment)
            commit()
        }

    }

    private fun nextFragment(mainManager: FragmentManager, fragment: Fragment) {
        val transaction: FragmentTransaction = mainManager.beginTransaction()
        transaction.apply {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            replace(R.id.enter_activity_fragment_container, fragment)
            commit()
        }
    }

    private fun TextInputLayout.setErrorOff() {
        this.error = null
        this.isErrorEnabled = false
    }

    private fun TextInputLayout.setErrorOn() {
        this.isErrorEnabled = true
        this.error = resources.getString(R.string.name_alert)
    }
}
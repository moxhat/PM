package com.madcrew.pravamobil.view.fragment.registration.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSignInBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.ClientAuthorizationRequest
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.hideKeyboard
import com.madcrew.pravamobil.utils.isOnline
import com.madcrew.pravamobil.utils.noInternet
import com.madcrew.pravamobil.view.activity.progress.ProgressViewModel
import com.madcrew.pravamobil.view.activity.progress.ProgressViewModelFactory
import com.madcrew.pravamobil.view.fragment.registration.enter.EnterFragment
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

        val repository = Repository()
        val viewModelFactory = SignInViewModelFactory(repository)
        val mViewModel = ViewModelProvider(this, viewModelFactory).get(SignInViewModel::class.java)

        val mainManager = parentFragmentManager
        val loginText = binding.signinLoginEditText
        val loginField = binding.signinLogin
        val passwordText = binding.signinPasswordEditText
        val passwordField = binding.signinPassword

        mViewModel.signResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                when (response.body()!!.status) {
                    "done" -> {
                        Preferences.setPrefsString("clientId", response.body()!!.client.id, requireContext())
                        val progressStatus = response.body()!!.client.progress
                        val name = response.body()!!.client.firstName
                        nextFragment(mainManager, GreetingsFragment(name, progressStatus))
                    }
                    "password" -> {
                        passwordField.isErrorEnabled = true
                        passwordField.error = resources.getString(R.string.wrong_password)
                    }
                    "notexist" -> {
                        passwordField.isErrorEnabled = true
                        passwordField.error = resources.getString(R.string.accaunt_not_found)
                    }
                }
            }
        })

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
            if (loginText.length() == 16 || passwordText.length() == 8) {
                if (isOnline(requireContext())){
                    mViewModel.clientAuthorization(
                        ClientAuthorizationRequest(
                            TOKEN,
                            loginText.text.toString(),
                            passwordText.text.toString()
                        )
                    )
                } else {
                    noInternet(requireContext())
                }
            } else {
                if (loginText.length() < 16) loginField.setErrorOn()
                if (passwordText.length() < 8)passwordField.setErrorOn()
            }
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

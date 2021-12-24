package com.madcrew.pravamobil.view.fragment.registration.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSignUpBinding
import com.madcrew.pravamobil.domain.BaseUrl
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.CallCodeRequest
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.fragment.registration.enter.EnterFragment
import com.madcrew.pravamobil.view.fragment.registration.smscode.SmsCodeFragment
import com.madcrew.pravamobil.view.fragment.registration.smscode.SmsCodeViewModel
import com.madcrew.pravamobil.view.fragment.registration.smscode.SmsCodeViewModelFactory
import java.util.*
import kotlin.concurrent.timerTask


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
        var name = ""

        val nameField = binding.signupName
        val phoneField = binding.signupPhone

        val repository = Repository()
        val viewModelFactory = SignUpViewModelFactory(repository)
        val mViewModel = ViewModelProvider(this, viewModelFactory).get(SignUpViewModel::class.java)

        val deviceId = Preferences.getPrefsString("deviceId", requireContext())!!

        Preferences.setPrefsString("progressStatus", "AddPassword", requireContext())
        Preferences.setPrefsString("checkData", "false", requireContext())

        phoneText.doOnTextChanged { _, _, _, _ ->
            if (phoneText.length() > 3) phoneField.setErrorOff()
            if (phoneText.length() == 16) nameText.requestFocus()
        }

        nameText.doOnTextChanged { _, _, _, _ ->
            name = nameText.text.toString()
            if (nameText.length() > 1) nameField.setErrorOff()
        }

        btGetCode.setOnClickListener {
            if (nameText.length() > 2 && name.first().toString() != " " && phoneText.length() == 16){
                Preferences.setPrefsString("firstName", nameText.text.toString(), requireContext())
                Preferences.setPrefsString("phoneNumber", phoneText.text.toString(), requireContext())
                Preferences.setPrefsString("login", phoneText.text.toString().substring(2, 16), requireContext())
                if (isOnline(requireContext())) {
                    mViewModel.getSmsCode(CallCodeRequest(BaseUrl.TOKEN, phoneText.text.toString(), deviceId))
                } else {
                    noInternet(requireContext())
                }

                btGetCode.hideKeyboard()
            } else {
                if (phoneText.length() < 16) phoneField.setErrorOn()
                if (nameText.length() < 2) nameField.setErrorOn()
            }
        }

        binding.btSignupBack.setOnClickListener {
            replaceFragment(EnterFragment(),R.anim.slide_right_in, R.anim.slide_right_out)
        }

        mViewModel.smsCodeResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                when (response.body()!!.status ) {
                    "done" ->   {
                        replaceFragment(SmsCodeFragment(phoneText.text.toString()), R.anim.fade_in, R.anim.fade_out)
                    }
                    "exist" -> {
                        Toast.makeText(
                            requireContext(),
                            resources.getString(R.string.already_exist),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    "fail"  -> {
                        Toast.makeText(
                            requireContext(),
                            resources.getString(R.string.try_later),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                showServerError(requireContext())
            }
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
        transaction.apply {
            setCustomAnimations(animationIn, animationOut)
            replace(R.id.enter_activity_fragment_container, fragment)
            commit()
        }
    }
}
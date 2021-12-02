package com.madcrew.pravamobil.view.fragment.registration.smscode

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSmsCodeBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.CallCodeRequest
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import java.util.*
import kotlin.concurrent.timerTask


class SmsCodeFragment(private var phoneNumber: String) : Fragment() {

    private var _binding: FragmentSmsCodeBinding? = null
    private val binding get() = _binding!!
    private lateinit var smsCode: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSmsCodeBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository()
        val viewModelFactory = SmsCodeViewModelFactory(repository)
        val mViewModel = ViewModelProvider(this, viewModelFactory).get(SmsCodeViewModel::class.java)

        val deviceId = Preferences.getPrefsString("deviceId", requireContext())!!

        if (isOnline(requireContext())){
            mViewModel.getSmsCode(CallCodeRequest(TOKEN, phoneNumber, deviceId))
        } else {
            noInternet(requireContext())
        }

        mViewModel.smsCodeResponse.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                if (response.body()!!.status == "done"){
                    smsCode = response.body()!!.code
                    Timer().schedule(timerTask  {
                        binding.btSmsCodeRetry.setEnable()
                    }, 30000)
                } else {
                    Toast.makeText(requireContext(), resources.getString(R.string.try_later), Toast.LENGTH_SHORT).show()
                }
            }
        })


        val codeChar1 = binding.smsCode1EditText
        val codeChar2 = binding.smsCode2EditText
        val codeChar3 = binding.smsCode3EditText
        val codeChar4 = binding.smsCode4EditText

        val code1 = binding.smsCode1
        val code2 = binding.smsCode2
        val code3 = binding.smsCode3
        val code4 = binding.smsCode4

        binding.invalidCodeAlert.setGone()

        code1.setOnClickListener {
            codeChar1.requestFocus()
        }
        code2.setOnClickListener {
            codeChar2.requestFocus()
        }
        code3.setOnClickListener {
            codeChar4.requestFocus()
        }
        code4.setOnClickListener {
            codeChar4.requestFocus()
        }

        codeChar1.doOnTextChanged { _, _, _, _ ->
            if (codeChar1.length() > 0) {
                codeChar2.requestFocus()
            } else {
                errorOff(code1, code2, code3, code4)
                codeChar1.requestFocus()
                codeChar1.text?.clear()
                clearCodeEnd(codeChar2, codeChar3, codeChar4)
            }
        }

        codeChar2.doOnTextChanged { _, _, _, _ ->
            if (codeChar2.length() > 0) {
                codeChar3.requestFocus()
            } else {
                codeChar1.requestFocus()
                codeChar1.text?.clear()
                clearCodeEnd(codeChar2, codeChar3, codeChar4)
            }
            if (codeChar1.length() == 0) {
                errorOff(code1, code2, code3, code4)
                codeChar1.requestFocus()
                codeChar1.setText(codeChar2.text.toString())
                clearCodeEnd(codeChar2, codeChar3, codeChar4)
            }
        }

        codeChar3.doOnTextChanged { _, _, _, _ ->
            if (codeChar3.length() > 0) {
                codeChar4.requestFocus()
            } else {
                codeChar1.requestFocus()
                codeChar1.setText(codeChar3.text.toString())
                clearCodeEnd(codeChar2, codeChar3, codeChar4)
            }
            if (codeChar1.length() == 0) {
                errorOff(code1, code2, code3, code4)
                codeChar1.requestFocus()
                codeChar1.setText(codeChar4.text.toString())
                clearCodeEnd(codeChar2, codeChar3, codeChar4)
            }
        }

        codeChar4.doOnTextChanged { _, _, _, _ ->
            if (codeChar4.length() > 0) {
                this.view?.hideKeyboard()
            } else {
                codeChar1.requestFocus()
                codeChar1.text?.clear()
                clearCodeEnd(codeChar2, codeChar3, codeChar4)
            }
            if (codeChar1.length() == 0) {
                errorOff(code1, code2, code3, code4)
                codeChar1.requestFocus()
                codeChar1.text?.clear()
                clearCodeEnd(codeChar2, codeChar3, codeChar4)
            }
            val codeValid =
                (codeChar1.text.toString() + codeChar2.text.toString() + codeChar3.text.toString() + codeChar4.text.toString()) == smsCode

            if (codeChar1.length() > 0 && codeChar2.length() > 0 && codeChar3.length() > 0 && codeChar4.length() > 0) {
                if (codeValid) {
                    starProgressActivity()
                } else {
                    errorOn(code1, code2, code3, code4, codeChar1, codeChar2, codeChar3,codeChar4)
                }
            }
        }
        binding.btSmsCodeRetry.setOnClickListener {
            if (isOnline(requireContext())){
                mViewModel.getSmsCode(CallCodeRequest(TOKEN, phoneNumber, TOKEN))
                binding.btSmsCodeRetry.setDisable()
            } else {
                noInternet(requireContext())
            }
        }
    }

    private fun errorOff(
        code1: TextInputLayout,
        code2: TextInputLayout,
        code3: TextInputLayout,
        code4: TextInputLayout,
    ) {
        code1.error = null
        code2.error = null
        code3.error = null
        code4.error = null
        code1.isErrorEnabled = false
        code2.isErrorEnabled = false
        code3.isErrorEnabled = false
        code4.isErrorEnabled = false
        binding.invalidCodeAlert.setGone()
    }

    private fun errorOn(
        code1: TextInputLayout,
        code2: TextInputLayout,
        code3: TextInputLayout,
        code4: TextInputLayout,
        codeChar1: EditText,
        codeChar2: EditText,
        codeChar3: EditText,
        codeChar4: EditText
    ) {
        code1.isErrorEnabled = true
        code2.isErrorEnabled = true
        code3.isErrorEnabled = true
        code4.isErrorEnabled = true
        code1.error = " "
        code2.error = " "
        code3.error = " "
        code4.error = " "
        Handler(Looper.getMainLooper()).postDelayed({
            codeChar1.text?.clear()
            codeChar2.text?.clear()
            codeChar3.text?.clear()
            codeChar4.text?.clear()
        }, 1500)

        binding.invalidCodeAlert.setVisible()

    }

    private fun starProgressActivity() {
        val intent = Intent(requireContext(), ProgressActivity::class.java)
        startActivity(intent)
        this.activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        this.activity?.finish()
    }

    private fun clearCodeEnd(
        code2: TextInputEditText,
        code3: TextInputEditText,
        code4: TextInputEditText
    ) {
        code2.text?.clear()
        code3.text?.clear()
        code4.text?.clear()
    }
}
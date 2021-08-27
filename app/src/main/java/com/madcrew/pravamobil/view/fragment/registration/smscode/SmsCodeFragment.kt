package com.madcrew.pravamobil.view.fragment.registration.smscode

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSmsCodeBinding
import com.madcrew.pravamobil.utils.hideKeyboard
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.utils.setVisible
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity


class SmsCodeFragment : Fragment() {

    private var _binding: FragmentSmsCodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSmsCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                (codeChar1.text.toString() + codeChar2.text.toString() + codeChar3.text.toString() + codeChar4.text.toString()) == "0000"

            if (codeChar1.length() > 0 && codeChar2.length() > 0 && codeChar3.length() > 0 && codeChar4.length() > 0) {
                if (codeValid) {
                    starProgressActivity()
                } else {
                    errorOn(code1, code2, code3, code4)
                }
            }
        }
    }

    private fun errorOff(
        code1: TextInputLayout,
        code2: TextInputLayout,
        code3: TextInputLayout,
        code4: TextInputLayout
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
        code4: TextInputLayout
    ) {
        code1.isErrorEnabled = true
        code2.isErrorEnabled = true
        code3.isErrorEnabled = true
        code4.isErrorEnabled = true
        code1.error = " "
        code2.error = " "
        code3.error = " "
        code4.error = " "
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
package com.madcrew.pravamobil.view.fragment.progress.addpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentAddPasswordBinding
import com.madcrew.pravamobil.databinding.FragmentSignUpBinding
import com.madcrew.pravamobil.utils.hideKeyboard


class AddPasswordFragment : Fragment() {

    private var _binding: FragmentAddPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstPassword = binding.addPasswordPassword2
        val secondPassword = binding.addPasswordPassword1
        val firstPasswordText = binding.addPasswordEditText2
        val secondPasswordText = binding.addPasswordEditText1

        firstPasswordText.doOnTextChanged{ text, start, before, count ->
            if(firstPasswordText.length() == 8){
                secondPasswordText.requestFocus()
            }
            if (firstPasswordText.length() > 1){
                secondPassword.error = null
                secondPassword.isErrorEnabled = false
            }
        }

        secondPasswordText.doOnTextChanged{ text, start, before, count ->
            if (secondPasswordText.length() == 8){
                this.view?.hideKeyboard()
            }
            if (secondPasswordText.length() > 1){
                secondPassword.error = null
                secondPassword.isErrorEnabled = false
            }
        }

        binding.btAddPasswordNext.setOnClickListener {
            if (firstPasswordText.length() == 8 && secondPasswordText.length() == 8){
                if (firstPasswordText.text.toString() == secondPasswordText.text.toString()){
                    Toast.makeText(requireContext(), "Пароль установлен", Toast.LENGTH_SHORT).show()
                } else {
                    secondPassword.isErrorEnabled = true
                    secondPassword.error = resources.getString(R.string.passwords_mismatch)
                }
            } else {
                secondPassword.isErrorEnabled = true
                secondPassword.error = resources.getString(com.madcrew.pravamobil.R.string.add_password_help)
            }
        }
    }

}
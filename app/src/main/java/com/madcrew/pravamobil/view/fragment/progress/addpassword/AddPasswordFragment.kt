package com.madcrew.pravamobil.view.fragment.progress.addpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentAddPasswordBinding
import com.madcrew.pravamobil.utils.hideKeyboard
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
                secondPassword.error = null
                secondPassword.isErrorEnabled = false
            }
        }

        secondPasswordText.doOnTextChanged { _, _, _, _ ->
            if (secondPasswordText.length() == 8) {
                this.view?.hideKeyboard()
            }
            if (secondPasswordText.length() > 1) {
                secondPassword.error = null
                secondPassword.isErrorEnabled = false
            }
        }

        binding.btAddPasswordNext.setOnClickListener {
//            chekPassword(
//                firstPasswordText,
//                secondPasswordText,
//                secondPassword,
//                mainManager,
//                EmailFragment()
//            )
            nextFragment(mainManager, EmailFragment())
        }
    }

    private fun chekPassword(
        firstPasswordText: TextInputEditText,
        secondPasswordText: TextInputEditText,
        secondPassword: TextInputLayout,
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        if (firstPasswordText.length() == 8 && secondPasswordText.length() == 8) {
            if (firstPasswordText.text.toString() == secondPasswordText.text.toString()) {
                Toast.makeText(requireContext(), "Пароль установлен", Toast.LENGTH_SHORT).show()
                nextFragment(fragmentManager, fragment)
            } else {
                secondPassword.isErrorEnabled = true
                secondPassword.error = resources.getString(R.string.passwords_mismatch)
            }
        } else {
            secondPassword.isErrorEnabled = true
            secondPassword.error = resources.getString(R.string.add_password_help)
        }
    }

    private fun nextFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out)
        transaction.remove(this)
        transaction.replace(R.id.progress_activity_fragment_container, fragment)
        transaction.commit()
    }
}
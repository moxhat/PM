package com.madcrew.pravamobil.view.fragment.progress.email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentEmailBinding
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.hideKeyboard
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.view.fragment.progress.category.CategoryFragment


class EmailFragment : Fragment() {

    private var _binding: FragmentEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager

        val emailText = binding.emailEditText
        val emailTextLayout = binding.emailTextInput

        emailText.doOnTextChanged { _, _, _, _ ->
            if (emailText.length() > 1) {
                emailTextLayout.isErrorEnabled = false
            }
        }

        binding.btEmailNext.setOnClickListener {
            if(emailText.length() > 4 && emailText.text!!.contains(Regex("[@.]"))){
                Preferences.setPrefsString("email", emailText.text.toString(), requireContext())
                this.view?.hideKeyboard()
                nextFragmentInProgress(mainManager, CategoryFragment())
            } else {
                emailTextLayout.isErrorEnabled = true
                emailTextLayout.error = resources.getString(R.string.email_error)
            }
        }
    }
}
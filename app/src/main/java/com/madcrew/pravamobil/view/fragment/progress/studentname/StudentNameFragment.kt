package com.madcrew.pravamobil.view.fragment.progress.studentname

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputLayout
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentStudentNameBinding
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.view.fragment.progress.documenttype.DocumentTypeFragment
import com.madcrew.pravamobil.view.fragment.progress.passport.PassportFragment

class StudentNameFragment(var title: Int = R.string.student, var type: String = "student") : Fragment() {

    private var _binding: FragmentStudentNameBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.studentNameTitle.setText(title)

        binding.btStudentNameNext.setOnClickListener {
            val mainManager= parentFragmentManager
            when(type){
                "student" -> nextFragmentInProgress(mainManager,PassportFragment())
                "parent" ->  nextFragmentInProgress(mainManager, DocumentTypeFragment(R.string.representatives, "parent"))
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
}
package com.madcrew.pravamobil.view.fragment.progress.notadult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentClientIsNotAdultBinding
import com.madcrew.pravamobil.databinding.FragmentPassportBinding
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.view.fragment.progress.passport.PassportFragment
import com.madcrew.pravamobil.view.fragment.progress.studentname.StudentNameFragment

class ClientIsNotAdultFragment : Fragment() {

    private var _binding: FragmentClientIsNotAdultBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientIsNotAdultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager

        binding.btNotadultNext.setOnClickListener {
            nextFragmentInProgress(mainManager, StudentNameFragment(R.string.student_representative, "parent"))
        }
    }
}
package com.madcrew.pravamobil.view.fragment.progress.passportscan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentPassportScanBinding
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.view.fragment.progress.checkdata.CheckDataFragment
import com.madcrew.pravamobil.view.fragment.progress.notadult.ClientIsNotAdultFragment
import com.madcrew.pravamobil.view.fragment.progress.snils.SnilsFragment


class PassportScanFragment(var titleText: Int = R.string.passport_scan_title, var typeOfPage: String = "passport" ) : Fragment() {

    private var _binding: FragmentPassportScanBinding? = null
    private val binding get() = _binding!!
    private var clientIsAdult = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPassportScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager

        binding.passportScanTitle.setText(titleText)

        binding.btPassportScanNext.setOnClickListener {
            when (typeOfPage){
                "passport" -> nextFragmentInProgress(mainManager, SnilsFragment())
                "registrationAddress" -> nextFragmentInProgress(mainManager, PassportScanFragment(R.string.your_photo, "avatar"))
                "avatar" -> if (clientIsAdult) {
                    nextFragmentInProgress(mainManager, CheckDataFragment())
                } else {
                    nextFragmentInProgress(mainManager, ClientIsNotAdultFragment())
                }
            }
        }
    }
}
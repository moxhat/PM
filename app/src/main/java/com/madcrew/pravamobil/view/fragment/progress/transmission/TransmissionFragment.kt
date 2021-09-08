package com.madcrew.pravamobil.view.fragment.progress.transmission

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentTransmissionBinding
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.view.dialog.InfoFragment
import com.madcrew.pravamobil.view.fragment.progress.theory.SelectTheoryFragment


class TransmissionFragment(private var selectedCategory: String) : Fragment() {

    private var _binging:FragmentTransmissionBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentTransmissionBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager

        binding.transmissionTitle.text = selectedCategory

        binding.btTransmissionMechanic.setOnClickListener {
            Preferences.setPrefsString("transmission", resources.getString(R.string.mechanic), requireContext())
            nextFragmentInProgress(mainManager, SelectTheoryFragment())
        }

        binding.btTransmissionAutomatic.setOnClickListener {
            Preferences.setPrefsString("transmission", resources.getString(R.string.automatic), requireContext())
            nextFragmentInProgress(mainManager,SelectTheoryFragment())
        }

        binding.btTransmissionHelp.setOnClickListener {
            showInfo()
        }
    }

    private fun showInfo(){
        val newFragment =
            InfoFragment()
        newFragment.show(parentFragmentManager, "InfoFragment")
    }
}
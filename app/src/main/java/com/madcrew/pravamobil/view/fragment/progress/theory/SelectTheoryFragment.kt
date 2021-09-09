package com.madcrew.pravamobil.view.fragment.progress.theory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSelectTheoryBinding
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.view.fragment.progress.filial.FilialFragment

class SelectTheoryFragment : Fragment() {

    private var _binding: FragmentSelectTheoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectTheoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager

        binding.btSelectTheoryOffline.setOnClickListener {
            Preferences.setPrefsString("theory", resources.getString(R.string.theory_offline), requireContext())
            nextFragmentInProgress(mainManager, FilialFragment())
        }
        binding.btSelectTheoryOnline.setOnClickListener {
            Preferences.setPrefsString("theory", resources.getString(R.string.theory_online), requireContext())
            nextFragmentInProgress(mainManager, FilialFragment())
        }
    }

}
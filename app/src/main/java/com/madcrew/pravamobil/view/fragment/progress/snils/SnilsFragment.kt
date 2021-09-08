package com.madcrew.pravamobil.view.fragment.progress.snils

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentPassportScanBinding
import com.madcrew.pravamobil.databinding.FragmentSnilsBinding
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.view.fragment.progress.address.AddressFragment


class SnilsFragment : Fragment() {

    private var _binding: FragmentSnilsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSnilsBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainManager = parentFragmentManager

        binding.btSnilsNext.setOnClickListener {
            Preferences.setPrefsString("snils", binding.snilsNumberText.text.toString(), requireContext())
            nextFragmentInProgress(mainManager, AddressFragment())
        }
    }


}
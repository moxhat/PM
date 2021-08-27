package com.madcrew.pravamobil.view.fragment.progress.selecttheory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSelectTheoryBinding

class SelectTheoryFragment : Fragment() {

    private var _binding: FragmentSelectTheoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectTheoryBinding.inflate(inflater, container, false)
        return binding.root
    }


}

package com.madcrew.pravamobil.view.fragment.education.home.practice.beforespravka

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentConfirmContractBinding
import com.madcrew.pravamobil.databinding.FragmentConfirmationSpravkaBinding
import com.madcrew.pravamobil.databinding.FragmentHomePracticeBinding
import com.madcrew.pravamobil.databinding.FragmentNoSpravkaBinding
import com.madcrew.pravamobil.view.dialog.SpravkaConfirmedDialogFragment


class ConfirmationSpravkaFragment : Fragment() {

    private var _binding: FragmentConfirmationSpravkaBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmationSpravkaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
package com.madcrew.pravamobil.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.madcrew.pravamobil.databinding.FragmentSpravkaImageDialogBinding
import com.madcrew.pravamobil.view.fragment.education.home.HomeFragment
import com.madcrew.pravamobil.view.fragment.education.home.practice.beforespravka.NoSpravkaFragment


class SpravkaImageDialogFragment : DialogFragment() {

    private var _binding: FragmentSpravkaImageDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpravkaImageDialogBinding.inflate(inflater, container, false)
        if (!(dialog == null || dialog!!.window == null)) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btSpravkaImageScanNext.setOnClickListener {
            this.dialog?.dismiss()
            (parentFragment as NoSpravkaFragment).setSpravkaConfirmation()
        }
    }
}
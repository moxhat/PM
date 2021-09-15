package com.madcrew.pravamobil.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.madcrew.pravamobil.databinding.FragmentSpravkaConfirmedDialogBinding
import com.madcrew.pravamobil.view.fragment.education.home.HomeFragment


class SpravkaConfirmedDialogFragment : DialogFragment() {

    private var _binding: FragmentSpravkaConfirmedDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpravkaConfirmedDialogBinding.inflate(inflater, container, false)
        if (!(dialog == null || dialog!!.window == null)) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btSpravkaConfirmedClose.setOnClickListener {
            (parentFragment as HomeFragment).setSpravkaConfirmed()
            this.dialog?.dismiss()
        }

    }
}
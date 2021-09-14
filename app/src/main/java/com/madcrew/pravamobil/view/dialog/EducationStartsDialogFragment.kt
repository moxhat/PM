package com.madcrew.pravamobil.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentEducationStartsDialogBinding
import com.madcrew.pravamobil.databinding.FragmentHomePracticeBinding


class EducationStartsDialogFragment : DialogFragment() {

    private var _binding: FragmentEducationStartsDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEducationStartsDialogBinding.inflate(inflater, container, false)
        if (!(dialog == null || dialog!!.window == null)) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btEducationStartsClose.setOnClickListener {
            this.dialog?.dismiss()
        }
    }


}
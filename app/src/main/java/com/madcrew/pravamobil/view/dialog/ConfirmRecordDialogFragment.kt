package com.madcrew.pravamobil.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.madcrew.pravamobil.databinding.FragmentConfirmRecordDialogBinding
import com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord.DrivingRecordFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord.DrivingRecordViewModel


class ConfirmRecordDialogFragment(var date: String, var name: String, var timeId: String, var timeTitle: String) : DialogFragment() {

    private var _binding: FragmentConfirmRecordDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmRecordDialogBinding.inflate(inflater, container, false)
        if (!(dialog == null || dialog!!.window == null)) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmRecordDate.text = date
        binding.confirmRecordInstructorName.text = name

        binding.btConfirmRecordNo.setOnClickListener {
            this.dialog?.dismiss()
        }

        binding.btConfirmRecordYes.setOnClickListener {
            (parentFragment as DrivingRecordFragment).confirmWriteToLesson(timeId, timeTitle)
            this.dialog?.dismiss()

        }
    }
}
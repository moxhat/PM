package com.madcrew.pravamobil.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentHistoryItemPropertiesDialogBinding
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionsActivity
import com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord.DrivingRecordFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.openlesson.OpenLessonFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.practice.HistoryPracticeFragment


class HistoryItemPropertiesDialogFragment(var date: String, var status: Int, var rating: Int = 0) : DialogFragment() {

    private var _binding: FragmentHistoryItemPropertiesDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryItemPropertiesDialogBinding.inflate(inflater, container, false)
        if (!(dialog == null || dialog!!.window == null)) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.historyItemPropertiesDate.text = date

        binding.btHistoryItemPropertiesMoreAbout.setOnClickListener {
            val parent = this.context as PracticeOptionsActivity
            parent.addOpenLesson(status)
            this.dialog?.dismiss()
        }

        binding.btHistoryItemPropertiesCancel.setOnClickListener {
            (parentFragment as HistoryPracticeFragment).showConfirm(date)
            this.dialog?.dismiss()
        }
    }

}
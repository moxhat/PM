package com.madcrew.pravamobil.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.madcrew.pravamobil.databinding.FragmentConfirmCancelDialogBinding
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionsActivity
import com.madcrew.pravamobil.view.fragment.education.home.practice.HomePracticeFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.practice.HistoryPracticeFragment
import java.text.FieldPosition


class ConfirmCancelDialogFragment(var date: String, var parent: String = "history", var position: Int = 0) : DialogFragment() {

    private var _binding: FragmentConfirmCancelDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmCancelDialogBinding.inflate(inflater, container, false)
        if (!(dialog == null || dialog!!.window == null)) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmCancelDate.text = date

        binding.btConfirmCancelYes.setOnClickListener {
            when (parent){
                "home" -> {
                    (parentFragment as HomePracticeFragment).cancelLesson()
                    this.dialog?.dismiss()
                }
                "history" -> {
                    val parentActivity = this.context as PracticeOptionsActivity
                    parentActivity.setLessonCancel(position)
                    this.dialog?.dismiss()
                }
            }

        }

        binding.btConfirmCancelNo.setOnClickListener {
            this.dialog?.dismiss()
        }
    }

}
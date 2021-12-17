package com.madcrew.pravamobil.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentInstructorCancelDialogBinding
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.openlesson.OpenLessonFragment


class InstructorCancelDialogFragment(var trigger: String = "cancel") : DialogFragment() {

    private var _binding: FragmentInstructorCancelDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstructorCancelDialogBinding.inflate(inflater, container, false)
        if (!(dialog == null || dialog!!.window == null)) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btInstructorCancelSend.setOnClickListener {
            if (trigger == "callback"){
                (parentFragment as OpenLessonFragment).setCommentText(binding.instructorCancelText.text.toString())
            }
            this.dialog?.dismiss()
        }

        if (trigger == "callback") {
            binding.instructorCancelTitle.setText(R.string.callback)
            binding.instructorCancelTitle2.setText(R.string.about_practice)
            binding.instructorCancelField.setHint(R.string.your_comment)
        } else {
            binding.instructorCancelTitle.setText(R.string.request)
            binding.instructorCancelTitle2.setText(R.string.on_instructor_change)
            binding.instructorCancelField.setHint(R.string.reason)
        }

        binding.btInstructorCancelClose.setOnClickListener {
            this.dialog?.dismiss()
        }
    }
}
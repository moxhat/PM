package com.madcrew.pravamobil.view.fragment.education.home.practice

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentHomePracticeBinding
import com.madcrew.pravamobil.utils.alphaDown
import com.madcrew.pravamobil.utils.alphaUp
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.utils.setVisible
import com.madcrew.pravamobil.view.activity.education.EducationActivity
import com.madcrew.pravamobil.view.dialog.InstructorCancelDialogFragment
import com.madcrew.pravamobil.view.dialog.SpravkaConfirmedDialogFragment


class HomePracticeFragment : Fragment() {

    private var _binding: FragmentHomePracticeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePracticeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homePracticeMenuConstraint.setGone()

        val parent = this.context as EducationActivity

        Glide.with(requireContext()).load(R.drawable.ic_man).circleCrop().into(binding.homePracticeInstructorAvatar)

        Handler(Looper.getMainLooper()).postDelayed({
            val confirmedDialog = SpravkaConfirmedDialogFragment("good")
            confirmedDialog.show(childFragmentManager, "SpravkaConfirmedDialogFragment")
        }, 1000)

        binding.btHomePracticeChangeInstructor.setOnClickListener {
            val changeDialog = InstructorCancelDialogFragment()
            changeDialog.show(childFragmentManager, "InstructorCancelDialogFragment")
        }

        binding.btHomePracticeMenu.setOnClickListener {
            showMenu(binding.homePracticeMenuConstraint)
        }
        binding.homePracticeMenuConstraint.setOnClickListener {
            hideMenu(it)
        }

        binding.btHomePracticeMenuSignupToPractice.setOnClickListener {
            hideMenu(binding.homePracticeMenuConstraint)
            parent.starPracticeOptionsActivity("record")
        }

        binding.btHomePracticeMenuLessonsHistory.setOnClickListener {
            hideMenu(binding.homePracticeMenuConstraint)
            parent.starPracticeOptionsActivity("history")
        }
    }

    private fun showMenu(view: View){
        view.setVisible()
        view.alphaUp(100)
    }

    private fun hideMenu(view: View){
        view.setGone()
        view.alphaDown(100)
    }

}
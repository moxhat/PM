package com.madcrew.pravamobil.view.fragment.education.home.theory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentHomeTheoryBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.showServerError
import com.madcrew.pravamobil.view.activity.education.EducationActivity
import com.madcrew.pravamobil.view.fragment.education.home.HomeFragment
import com.madcrew.pravamobil.view.fragment.education.home.practice.HomePracticeViewModel
import com.madcrew.pravamobil.view.fragment.education.home.practice.HomePracticeViewModelFactory
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.LessonHistoryFragment


class HomeTheoryFragment : Fragment() {

    private var _binding: FragmentHomeTheoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeTheoryBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        val home = (parentFragment as HomeFragment)

        val teacherAva = binding.homeTheoryTeacherAvatar
        val teacherN = binding.homeTheoryTeacherName

        home.hViewModel.getTheoryHistory(SpravkaStatusRequest(TOKEN, schoolId, clientId))

        home.hViewModel.theoryHistory.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful){
                if (response.body()!!.status == "done"){
                    val nearestLesson = response.body()!!.schedule?.get(0)
                    if (nearestLesson != null){
                        val teacherName = nearestLesson.secondName + " " + nearestLesson.name + " " + nearestLesson.patronymic
                        Glide.with(requireContext()).load(nearestLesson.photoUrl).circleCrop().into(teacherAva)
                        teacherN.text = teacherName
                    }

                } else {
                    showServerError(requireContext())
                }
            }
            else {
                showServerError(requireContext())
            }
        })

        binding.btHomeTheoryLessonsHistory.setOnClickListener {
            (this.context as EducationActivity).starPracticeOptionsActivity("theory","history")
        }
    }
}
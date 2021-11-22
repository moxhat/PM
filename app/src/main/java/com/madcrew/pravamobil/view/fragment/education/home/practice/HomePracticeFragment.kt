package com.madcrew.pravamobil.view.fragment.education.home.practice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentHomePracticeBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.education.EducationActivity
import com.madcrew.pravamobil.view.dialog.InstructorCancelDialogFragment
import com.madcrew.pravamobil.view.dialog.SpravkaConfirmedDialogFragment
import com.madcrew.pravamobil.view.fragment.education.home.HomeFragment


class HomePracticeFragment : Fragment() {

    private var _binding: FragmentHomePracticeBinding? = null
    private val binding get() = _binding!!

    private var instructorPhoneNumber = ""

    lateinit var mViewModel: HomePracticeViewModel

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

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        val parent = this.context as EducationActivity
        val home = (parentFragment as HomeFragment)

        val instructorAvatar = binding.homePracticeInstructorAvatar
        val instructorName = binding.homePracticeInstructorName
        val instructorCar = binding.homePracticeInstructorCar
        val instructorRate = binding.homePracticeInstructorRate

        val repository = Repository()
        val viewModelFactory = HomePracticeViewModelFactory(repository)
        mViewModel = ViewModelProvider(this, viewModelFactory).get(HomePracticeViewModel::class.java)

        if (isOnline(requireContext())){
            mViewModel.getPracticeHistory(SpravkaStatusRequest(TOKEN, schoolId, clientId))
        } else {
            noInternet(requireContext())
        }

        mViewModel.lessonHistoryPracticeResponse.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                when (response.body()!!.status){
                    "done" -> {
                        if (response.body()!!.history?.size != 0){
                        val nearestLesson = response.body()!!.history?.get(0)!!
                        Glide.with(requireContext()).load(nearestLesson.photoUrl).circleCrop()
                            .into(instructorAvatar)
                            val insName = nearestLesson.secondName + " " + nearestLesson.name + " " + nearestLesson.patronymic
                        instructorName.text = insName
                        instructorCar.text = nearestLesson.car
                        instructorRate.text = nearestLesson.instRating
                            val titleText = "${dateConverterForTitle(nearestLesson.date.toString(), requireContext())} ${nearestLesson.dateTIme} ${nearestLesson.place}"
                            home.setTitle(resources.getString(R.string.nearest_lesson), titleText)
                            instructorPhoneNumber = nearestLesson.phone.toString()
                            binding.btHomePracticeCallInstructor.setEnable()
                            binding.btHomePracticeChangeInstructor.setEnable()
                        } else {
                            Glide.with(requireContext()).load(R.drawable.ic_man).circleCrop()
                                .into(binding.homePracticeInstructorAvatar)
                            instructorName.text = resources.getString(R.string.no)
                            instructorCar.text = resources.getString(R.string.no)
                            instructorRate.text = resources.getString(R.string.no)
                            home.setTitle(resources.getString(R.string.nearest_lesson), resources.getString(R.string.no_lesson))
                            binding.btHomePracticeCallInstructor.setDisable()
                            binding.btHomePracticeChangeInstructor.setDisable()
                        }
                    }
                    "fail" -> {
                        Toast.makeText(requireContext(), resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                        Glide.with(requireContext()).load(R.drawable.ic_man).circleCrop()
                            .into(binding.homePracticeInstructorAvatar)
                        instructorName.text = resources.getString(R.string.no)
                        instructorCar.text = resources.getString(R.string.no)
                        instructorRate.text = resources.getString(R.string.no)
                        home.setTitle(resources.getString(R.string.nearest_lesson), resources.getString(R.string.no_lesson))
                        binding.btHomePracticeCallInstructor.setDisable()
                        binding.btHomePracticeChangeInstructor.setDisable()
                    }
                }
            }
        })

        val confirmedDialog = SpravkaConfirmedDialogFragment("good")
        confirmedDialog.show(childFragmentManager, "SpravkaConfirmedDialogFragment")

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

        binding.btHomePracticeCallInstructor.setOnClickListener{
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data =
                Uri.parse("tel:$instructorPhoneNumber")
            startActivity(callIntent)
        }
    }

    private fun showMenu(view: View) {
        view.setVisible()
        view.alphaUp(100)
    }

    private fun hideMenu(view: View) {
        view.setGone()
        view.alphaDown(100)
    }

}
package com.madcrew.pravamobil.view.fragment.education.home.practice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentHomePracticeBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.LessonCancelRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.education.EducationActivity
import com.madcrew.pravamobil.view.dialog.ConfirmCancelDialogFragment
import com.madcrew.pravamobil.view.dialog.InstructorCancelDialogFragment
import com.madcrew.pravamobil.view.dialog.SpravkaConfirmedDialogFragment
import com.madcrew.pravamobil.view.fragment.education.home.HomeFragment


class HomePracticeFragment : Fragment() {

    private var _binding: FragmentHomePracticeBinding? = null
    private val binding get() = _binding!!

    private var instructorPhoneNumber = ""

    lateinit var mViewModel: HomePracticeViewModel

    private var instructorId = "0"
    private var timeId = "0"
    private var timeTitle = "0"
    private var cancelTitle = ""
    private var nearestDate = ""

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
            home.hViewModel.getPracticeHistory(SpravkaStatusRequest(TOKEN, schoolId, clientId))
        } else {
            noInternet(requireContext())
        }

        home.hViewModel.lessonHistoryPracticeResponse.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                when (response.body()!!.status){
                    "done" -> {
                        if (response.body()!!.history?.size != 0){
                        val nearestLesson = response.body()!!.history?.get(0)!!
                        Glide.with(requireContext()).load(nearestLesson.photoUrl).circleCrop().diskCacheStrategy(
                            DiskCacheStrategy.ALL)
                            .into(instructorAvatar)
                            val insName = nearestLesson.secondName + " " + nearestLesson.name + " " + nearestLesson.patronymic
                        instructorName.text = insName
                        instructorCar.text = nearestLesson.car
                        instructorRate.text = nearestLesson.instRating
                            instructorId = nearestLesson.instructor_id.toString()
                            timeId = nearestLesson.timeID.toString()
                            timeTitle = nearestLesson.time.toString()
                            cancelTitle = "${dateConverterForTitle(nearestLesson.date.toString(), requireContext())} ${nearestLesson.time}"
                            nearestDate = nearestLesson.date.toString()
                            instructorPhoneNumber = nearestLesson.phone.toString()
                            binding.btHomePracticeCallInstructor.setEnable()
                            binding.btHomePracticeChangeInstructor.setEnable()
                        } else {
                            Glide.with(requireContext()).load(R.drawable.ic_man).circleCrop()
                                .into(binding.homePracticeInstructorAvatar)
                            instructorName.text = resources.getString(R.string.no)
                            instructorCar.text = resources.getString(R.string.no)
                            instructorRate.text = resources.getString(R.string.no)
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
                        binding.btHomePracticeCallInstructor.setDisable()
                        binding.btHomePracticeChangeInstructor.setDisable()
                    }
                }
            }
        })

        home.hViewModel.lessonCancelResponse.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                when (response.body()!!.status){
                    "done" ->{
                        if (isOnline(requireContext())){
                            home.hViewModel.getPracticeHistory(SpravkaStatusRequest(TOKEN, schoolId, clientId))
                        } else {
                            noInternet(requireContext())
                        }
                        Toast.makeText(requireContext(), resources.getString(R.string.lesson_canceled), Toast.LENGTH_SHORT).show()
                    }
                    "error" -> Toast.makeText(requireContext(), response.body()!!.error.toString(), Toast.LENGTH_SHORT).show()
                    "fail" -> Toast.makeText(requireContext(), resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        })

        if (Preferences.getPrefsString("SpravkaConfirmedDialogFragment", requireContext()) == "0"){
            val confirmedDialog = SpravkaConfirmedDialogFragment("good")
            confirmedDialog.show(childFragmentManager, "SpravkaConfirmedDialogFragment")
        }

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
            parent.starPracticeOptionsActivity("practice","record")
        }

        binding.btHomePracticeMenuLessonsHistory.setOnClickListener {
            hideMenu(binding.homePracticeMenuConstraint)
            parent.starPracticeOptionsActivity("practice","history")
        }

        binding.btHomePracticeCallInstructor.setOnClickListener{
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data =
                Uri.parse("tel:$instructorPhoneNumber")
            startActivity(callIntent)
        }

        binding.btHomePracticeMenuLearnCancel.setOnClickListener{
            hideMenu(binding.homePracticeMenuConstraint)
            showConfirm(cancelTitle, "home")
        }
    }

    fun cancelLesson(){
        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
        if (isOnline(requireContext())){
            mViewModel.setLessonCancel(LessonCancelRequest(TOKEN, schoolId, instructorId, clientId, nearestDate, timeId, timeTitle))
        } else {
            noInternet(requireContext())
        }
    }

    private fun showConfirm(date:String, parent: String){
        Handler(Looper.getMainLooper()).postDelayed({
            val cancelConfirm = ConfirmCancelDialogFragment(date, parent)
            cancelConfirm.show(childFragmentManager, "ConfirmCancelDialogFragment")
        }, 50)
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
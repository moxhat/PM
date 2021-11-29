package com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.openlesson

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentOpenLessonBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.LessonRateRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionViewModel
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionViewModelFactory
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionsActivity
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.dialog.InstructorCancelDialogFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.practice.HistoryPracticeFragment
import java.text.FieldPosition


class OpenLessonFragment(var date: String, var status: String, var rating: Double, var position: Int) : Fragment() {

    private var _binding: FragmentOpenLessonBinding? = null
    private val binding get() = _binding!!
    lateinit var mViewModel: OpenLessonViewModel
    private var additionalRate = mutableMapOf(Pair("clean", false), Pair("politeness", false), Pair("timing", false), Pair("quality", false))
    private var comment = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpenLessonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parent = this.context as PracticeOptionsActivity
        var currentLesson = parent.mViewModel.lessonHistoryPracticeResponse.value!!.body()!!.history?.get(position)!!


        val repository = Repository()
        val viewModelFactory = OpenLessonViewModelFactory(repository)
        mViewModel = ViewModelProvider(this, viewModelFactory).get(OpenLessonViewModel::class.java)

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        val rateSheet = binding.openLessonRatingSheet
        val btRateClose = binding.btOpenLessonCloseRating
        val ratingSheetBar = binding.openLessonRatingSheetRatingBar
        val instructorAvatar = binding.openLessonInstructorAvatar
        val instructorRating = binding.openLessonInstructorRate
        val instructorName = binding.openLessonInstructorName
        val instructorCar = binding.openLessonInstructorCar
        val instructorComment = binding.openLessonRateComment

        Glide.with(requireContext()).load(currentLesson.photoUrl).circleCrop()
            .into(instructorAvatar)

        binding.openLessonTitleDate.text = date
        binding.openLessonTitleStatus.text = status

        instructorRating.text = currentLesson.instRating
        instructorName.text = "${currentLesson.secondName} ${currentLesson.name} ${currentLesson.patronymic}"
        instructorCar.text = currentLesson.car

        btRateClose.setGone()
        val mBottomRateSheet = BottomSheetBehavior.from(rateSheet)

        mBottomRateSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        mBottomRateSheet.isHideable = true

        hideAll()

        if (rating > 0.1) {
            binding.openLessonRatingBar.rating = rating.toFloat()
            binding.openLessonRateComment.text = currentLesson.comment
        }

        when (status) {
            "Назначено" -> setCancel()
            "Пройдено" -> {
                if (rating == 0.0){
                    setUnrated()
                } else {
                    setRated()
                }
            }
            else -> setRated()
        }

        parent.mViewModel.lessonHistoryPracticeResponse.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                if (response.body()!!.status == "done"){
                    currentLesson = parent.mViewModel.lessonHistoryPracticeResponse.value!!.body()!!.history?.get(position)!!
                    instructorRating.text = currentLesson.instRating
                    instructorName.text = "${currentLesson.secondName} ${currentLesson.name} ${currentLesson.patronymic}"
                    instructorCar.text = currentLesson.car

                    if (rating > 0.1) {
                        binding.openLessonRatingBar.rating = rating.toFloat()
                        instructorRating.text = currentLesson.instRating
                    }
                }
            } else {
                showServerError(requireContext())
            }
        })

        mViewModel.lessonRateResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                if (response.body()!!.status == "done"){
                    parent.mViewModel.getPracticeHistory(SpravkaStatusRequest(TOKEN, schoolId, clientId))
                    setRated()
                }
            } else {
                showServerError(requireContext())
            }
        })

        binding.btOpenLessonLessonCancel.setOnClickListener {
            parent.mViewModel.lessonCancelResponse.observe(viewLifecycleOwner, {response ->
                if (response.isSuccessful){
                    when (response.body()!!.status){
                        "done" -> {
                            binding.btOpenLessonBack.performClick()
                        }
                        "error" -> Toast.makeText(requireContext(), response.body()!!.error.toString(), Toast.LENGTH_SHORT).show()
                        "fail" -> Toast.makeText(requireContext(), resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                    }
                }
            })
            parent.showConfirm(date, position)
        }

        binding.btOpenLessonBack.setOnClickListener {
            parent.mViewModel.lessonHistoryPracticeResponse.removeObservers(viewLifecycleOwner)
            val mainManager = parentFragmentManager
            val transaction: FragmentTransaction = mainManager.beginTransaction()
            transaction.remove(this)
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            transaction.commit()
        }

        binding.openLessonMainConstraint.setOnClickListener {

        }

        binding.btOpenLessonRateLesson.setOnClickListener {
            btRateClose.alphaUp(100)
            btRateClose.setVisible()
            mBottomRateSheet.state = BottomSheetBehavior.STATE_EXPANDED
        }

        btRateClose.setOnClickListener {
            btRateClose.alphaDown(100)
            btRateClose.setGone()
            mBottomRateSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.btOpenLessonCallInstructor.setOnClickListener{
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data =
                Uri.parse("tel:" + "${currentLesson.phone}")
            startActivity(callIntent)
        }

        binding.openLessonRatingContentConstraint.setOnClickListener {

        }

        ratingSheetBar.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                when (rating) {
                    0f -> {
                        hideAll()
                        binding.btRateSheetDone.setDisable()
                    }
                    5f -> {
                        showGood()
                        binding.btRateSheetDone.setEnable()
                    }
                    else -> {
                        showBad()
                        binding.btRateSheetDone.setEnable()
                    }
                }
            }

        binding.btRateSheetDone.setOnClickListener {
            val instructorId = parent.mViewModel.lessonHistoryPracticeResponse.value!!.body()!!.history!![position].instructor_id!!
            val timeId = parent.mViewModel.lessonHistoryPracticeResponse.value!!.body()!!.history!![position].timeID!!
            val rating = ratingSheetBar.rating.toInt()
            val date = parent.mViewModel.lessonHistoryPracticeResponse.value!!.body()!!.history!![position].date!!
            val features = mutableListOf<String>()
                additionalRate.forEach { i ->
                    if (i.value){
                        features.add(i.key)
                    }
                }
            mViewModel.setLessonRate(LessonRateRequest(TOKEN, schoolId.toInt(), clientId.toInt(), instructorId.toInt(), date, timeId, features, comment, rating))
            btRateClose.alphaDown(100)
            btRateClose.setGone()
            setRated()
            binding.openLessonRatingBar.rating = binding.openLessonRatingSheetRatingBar.rating
            mBottomRateSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.btRateSheetAddCallback.setOnClickListener {
            val callBackDialog = InstructorCancelDialogFragment("callback")
            callBackDialog.show(childFragmentManager, "InstructorCancelDialogFragment")
        }

        binding.btDirtyCar.setOnClickListener {
            if (additionalRate["clean"] == false){
                binding.btDirtyCar.setBackgroundResource(R.drawable.ic_rectangle_blue_transparent)
                additionalRate["clean"] = true
            } else {
                binding.btDirtyCar.setBackgroundResource(R.drawable.ic_rectangle_transparent)
                additionalRate["clean"] = false
            }
        }

        binding.btRudeInstructor.setOnClickListener {
            if (additionalRate["politeness"] == false){
                binding.btRudeInstructor.setBackgroundResource(R.drawable.ic_rectangle_blue_transparent)
                additionalRate["politeness"] = true
            } else {
                binding.btRudeInstructor.setBackgroundResource(R.drawable.ic_rectangle_transparent)
                additionalRate["politeness"] = false
            }
        }

        binding.btLate.setOnClickListener {
            if (additionalRate["timing"] == false){
                binding.btLate.setBackgroundResource(R.drawable.ic_rectangle_blue_transparent)
                additionalRate["timing"] = true
            } else {
                binding.btLate.setBackgroundResource(R.drawable.ic_rectangle_transparent)
                additionalRate["timing"] = false
            }
        }

        binding.btNoComment.setOnClickListener {
            if (additionalRate["quality"] == false){
                binding.btNoComment.setBackgroundResource(R.drawable.ic_rectangle_blue_transparent)
                additionalRate["quality"] = true
            } else {
                binding.btNoComment.setBackgroundResource(R.drawable.ic_rectangle_transparent)
                additionalRate["quality"] = false
            }
        }

        binding.btClearCar.setOnClickListener {
            if (additionalRate["clean"] == false){
                binding.btClearCar.setBackgroundResource(R.drawable.ic_rectangle_blue_transparent)
                additionalRate["clean"] = true
            } else {
                binding.btClearCar.setBackgroundResource(R.drawable.ic_rectangle_transparent)
                additionalRate["clean"] = false
            }
        }

        binding.btPoliteness.setOnClickListener {
            if (additionalRate["politeness"] == false){
                binding.btPoliteness.setBackgroundResource(R.drawable.ic_rectangle_blue_transparent)
                additionalRate["politeness"] = true
            } else {
                binding.btPoliteness.setBackgroundResource(R.drawable.ic_rectangle_transparent)
                additionalRate["politeness"] = false
            }
        }

        binding.btPunctuality.setOnClickListener {
            if (additionalRate["timing"] == false){
                binding.btPunctuality.setBackgroundResource(R.drawable.ic_rectangle_blue_transparent)
                additionalRate["timing"] = true
            } else {
                binding.btPunctuality.setBackgroundResource(R.drawable.ic_rectangle_transparent)
                additionalRate["timing"] = false
            }
        }

        binding.btLessonQuality.setOnClickListener {
            if (additionalRate["quality"] == false){
                binding.btLessonQuality.setBackgroundResource(R.drawable.ic_rectangle_blue_transparent)
                additionalRate["quality"] = true
            } else {
                binding.btLessonQuality.setBackgroundResource(R.drawable.ic_rectangle_transparent)
                additionalRate["quality"] = false
            }
        }
    }

    private fun cleanAdditionalRating(){
        additionalRate = mutableMapOf(Pair("clean", false), Pair("politeness", false), Pair("timing", false), Pair("quality", false))
        binding.btDirtyCar.setBackgroundResource(R.drawable.ic_rectangle_transparent)
        binding.btRudeInstructor.setBackgroundResource(R.drawable.ic_rectangle_transparent)
        binding.btLate.setBackgroundResource(R.drawable.ic_rectangle_transparent)
        binding.btNoComment.setBackgroundResource(R.drawable.ic_rectangle_transparent)
        binding.btClearCar.setBackgroundResource(R.drawable.ic_rectangle_transparent)
        binding.btPoliteness.setBackgroundResource(R.drawable.ic_rectangle_transparent)
        binding.btPunctuality.setBackgroundResource(R.drawable.ic_rectangle_transparent)
        binding.btLessonQuality.setBackgroundResource(R.drawable.ic_rectangle_transparent)
    }

    private fun setCancel() {
        binding.openLessonLessonCancelTitle.setVisible()
        binding.btOpenLessonLessonCancel.setVisible()
        binding.openLessonRateTitle.setGone()
        binding.btOpenLessonRateLesson.setGone()
        binding.openLessonInstructorRatingTitle.setGone()
        binding.openLessonInstructorRatingText.setGone()
        binding.openLessonRatingBar.setGone()
        binding.openLessonRateCommentTitle.setGone()
        binding.openLessonRateComment.setGone()
    }

    private fun setUnrated() {
        binding.openLessonLessonCancelTitle.setGone()
        binding.btOpenLessonLessonCancel.setGone()
        binding.openLessonRateTitle.setVisible()
        binding.btOpenLessonRateLesson.setVisible()
        binding.btOpenLessonRateLesson.alpha = 1f
        binding.btOpenLessonRateLesson.isEnabled = true
        binding.openLessonInstructorRatingTitle.setVisible()
        binding.openLessonInstructorRatingText.setVisible()
        binding.openLessonRatingBar.setGone()
        binding.openLessonRateCommentTitle.setVisible()
        binding.openLessonRateComment.setVisible()
    }

    private fun setRated() {
        binding.openLessonLessonCancelTitle.setGone()
        binding.btOpenLessonLessonCancel.setGone()
        binding.openLessonRateTitle.setVisible()
        binding.btOpenLessonRateLesson.setVisible()
        binding.btOpenLessonRateLesson.alpha = 0.5f
        binding.btOpenLessonRateLesson.isEnabled = false
        binding.openLessonInstructorRatingTitle.setVisible()
        binding.openLessonInstructorRatingText.setGone()
        binding.openLessonRatingBar.setVisible()
        binding.openLessonRateCommentTitle.setVisible()
        binding.openLessonRateComment.setVisible()
    }

    private fun showGood() {
        cleanAdditionalRating()
        binding.rateSheetRatingGood.setVisible()
        binding.rateSheetRatingBad.setGone()
    }

    private fun showBad() {
        cleanAdditionalRating()
        binding.rateSheetRatingGood.setGone()
        binding.rateSheetRatingBad.setVisible()
    }

    private fun hideAll() {
        binding.rateSheetRatingGood.setGone()
        binding.rateSheetRatingBad.setGone()
    }

    fun setCommentText(text: String){
        comment = text
    }

}
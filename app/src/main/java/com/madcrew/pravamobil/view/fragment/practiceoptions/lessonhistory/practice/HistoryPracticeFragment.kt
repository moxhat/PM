package com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.practice

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.LessonHistoryRecyclerAdapter
import com.madcrew.pravamobil.databinding.FragmentHistoryPracticeBinding
import com.madcrew.pravamobil.domain.BaseUrl
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.LessonsData
import com.madcrew.pravamobil.models.requestmodels.LessonCancelRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.dateConverterForTitle
import com.madcrew.pravamobil.utils.isOnline
import com.madcrew.pravamobil.utils.noInternet
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionsActivity
import com.madcrew.pravamobil.view.dialog.ConfirmCancelDialogFragment
import com.madcrew.pravamobil.view.dialog.HistoryItemPropertiesDialogFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord.DrivingRecordFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.LessonHistoryFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.openlesson.OpenLessonFragment


class HistoryPracticeFragment : Fragment(), LessonHistoryRecyclerAdapter.OnStatusClickListener {

    private var _binding: FragmentHistoryPracticeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mLessonsList:MutableList<LessonsData>

    private lateinit var mAdapter: LessonHistoryRecyclerAdapter
    private var selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryPracticeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parent = this.context as PracticeOptionsActivity

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        if (isOnline(requireContext())){
            parent.mViewModel.getPracticeHistory(SpravkaStatusRequest(BaseUrl.TOKEN, schoolId, clientId))
        } else {
            noInternet(requireContext())
        }

        parent.mViewModel.lessonHistoryPracticeResponse.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                when (response.body()!!.status){
                    "done" -> {
                        val tmpLessonList = mutableListOf<LessonsData>()
                        for (i in response.body()!!.history!!){
                            tmpLessonList.add(LessonsData("${dateConverterForTitle(i.date.toString(), requireContext())} ${i.time}", "${i.secondName} ${i.name?.get(0)}. ${i.patronymic?.get(0)}.", i.status, i.rating!!.toDouble()))
                        }
                        mLessonsList = tmpLessonList
                        mAdapter = LessonHistoryRecyclerAdapter(mLessonsList, this)
                        binding.historyPracticeRecycler.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = mAdapter
                        }
                    }
                    "fail" -> {
                        Toast.makeText(requireContext(), resources.getText(R.string.error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        parent.mViewModel.lessonCancelResponse.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                when (response.body()!!.status){
                    "done" -> {
                        setPositionCanceled(selectedPosition)
                        Toast.makeText(requireContext(), resources.getString(R.string.lesson_canceled), Toast.LENGTH_SHORT).show()
                    }
                    "error" -> Toast.makeText(requireContext(), response.body()!!.error.toString(), Toast.LENGTH_SHORT).show()
                    "fail" -> Toast.makeText(requireContext(), resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onStatusClick(itemView: View?, position: Int) {
        val status = mLessonsList[position].status.toString()
        val rating = mLessonsList[position].rating!!.toDouble()
        val date = mLessonsList[position].date!!
        selectedPosition = position
        if (mLessonsList[position].status == "Назначено"){
            val propertiesDialog = HistoryItemPropertiesDialogFragment(date, status, position, rating)
            propertiesDialog.show(childFragmentManager, "HistoryItemPropertiesDialogFragment")
        } else {
            val parent = this.context as PracticeOptionsActivity
            parent.addOpenLesson(date, status, rating, position)
        }
    }



    fun setPositionCanceled(position: Int){
        mLessonsList[position].status = "Отмена"
        mAdapter.notifyItemChanged(selectedPosition)
    }

}
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
import com.madcrew.pravamobil.models.LessonsData
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionsActivity
import com.madcrew.pravamobil.view.dialog.ConfirmCancelDialogFragment
import com.madcrew.pravamobil.view.dialog.HistoryItemPropertiesDialogFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord.DrivingRecordFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.openlesson.OpenLessonFragment


class HistoryPracticeFragment : Fragment(), LessonHistoryRecyclerAdapter.OnStatusClickListener {

    private var _binding: FragmentHistoryPracticeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mLessonsList:MutableList<LessonsData>

    private lateinit var mAdapter: LessonHistoryRecyclerAdapter


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

        mLessonsList = mutableListOf(
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Пройдено", 3),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Неявка",0),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Отмена",0),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Неявка", 0),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Назначено", 0),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Пройдено", 0),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Пройдено", 3),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Неявка",0),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Отмена",0),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Неявка", 0),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Назначено", 0),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Пройдено", 0)
        )

        mAdapter = LessonHistoryRecyclerAdapter(mLessonsList, this)


        binding.historyPracticeRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }

    override fun onStatusClick(itemView: View?, position: Int) {
        //            0 -> setCancel()
        //            1 -> setUnrated()
        //            else -> setRated()
        var status = when (mLessonsList[position].status){
            "Назначено" -> 0
            "Пройдено" -> {
                if (mLessonsList[position].rating == 0){
                    1
                } else {
                    mLessonsList[position].rating
                }
            }
            else -> 1
        }
        if (mLessonsList[position].status == "Назначено"){
            status = 0
            val date = mLessonsList[position].date.toString()
            val propertiesDialog = HistoryItemPropertiesDialogFragment(date, status)
            propertiesDialog.show(childFragmentManager, "HistoryItemPropertiesDialogFragment")
        } else {
            val parent = this.context as PracticeOptionsActivity
            parent.addOpenLesson(status)
        }
    }

    fun showConfirm(date:String){
        Handler(Looper.getMainLooper()).postDelayed({
            val cancelConfirm = ConfirmCancelDialogFragment(date)
            cancelConfirm.show(childFragmentManager, "ConfirmCancelDialogFragment")
        }, 50)
    }

}
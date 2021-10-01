package com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.practice

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.LessonHistoryRecyclerAdapter
import com.madcrew.pravamobil.databinding.FragmentHistoryPracticeBinding
import com.madcrew.pravamobil.models.LessonsData
import com.madcrew.pravamobil.view.dialog.ConfirmCancelDialogFragment
import com.madcrew.pravamobil.view.dialog.HistoryItemPropertiesDialogFragment


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
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Пройдено"),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Неявка"),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Отмена"),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Неявка"),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Назначено"),

        )

        mAdapter = LessonHistoryRecyclerAdapter(mLessonsList, this)


        binding.historyPracticeRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }

    override fun onStatusClick(itemView: View?, position: Int) {
        if (mLessonsList[position].status == "Назначено"){
            val date = mLessonsList[position].date.toString()
            val propertiesDialog = HistoryItemPropertiesDialogFragment(date)
            propertiesDialog.show(childFragmentManager, "HistoryItemPropertiesDialogFragment")
        }
    }

    fun showConfirm(date:String){
        Handler(Looper.getMainLooper()).postDelayed({
            val cancelConfirm = ConfirmCancelDialogFragment(date)
            cancelConfirm.show(childFragmentManager, "ConfirmCancelDialogFragment")
        }, 50)
    }

}
package com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.theory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.LessonHistoryRecyclerAdapter
import com.madcrew.pravamobil.databinding.FragmentHistoryPracticeBinding
import com.madcrew.pravamobil.databinding.FragmentHistoryTheoryBinding
import com.madcrew.pravamobil.models.LessonsData


class HistoryTheoryFragment : Fragment(), LessonHistoryRecyclerAdapter.OnStatusClickListener {

    private var _binding: FragmentHistoryTheoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAdapter: LessonHistoryRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryTheoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mLessonsList = mutableListOf(
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Пройдено"),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Неявка"),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Отмена"),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Неявка"),
            LessonsData("12.10.21 (вт)", "Питт Б.Б.", "Пройдено"),

            )

        mAdapter = LessonHistoryRecyclerAdapter(mLessonsList, this)


        binding.historyTheoryRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }

    override fun onStatusClick(itemView: View?, position: Int) {
        Toast.makeText(requireContext(), "Теория", Toast.LENGTH_SHORT).show()
    }
}
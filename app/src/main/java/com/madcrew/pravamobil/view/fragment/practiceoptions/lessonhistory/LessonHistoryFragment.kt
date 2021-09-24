package com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentDrivingRecordBinding
import com.madcrew.pravamobil.databinding.FragmentLessonHistoryBinding


class LessonHistoryFragment : Fragment() {

    private var _binding: FragmentLessonHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLessonHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

}
package com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.madcrew.pravamobil.databinding.FragmentDrivingRecordBinding


class DrivingRecordFragment : Fragment() {

    private var _binding: FragmentDrivingRecordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrivingRecordBinding.inflate(inflater, container, false)
        return binding.root
    }


}
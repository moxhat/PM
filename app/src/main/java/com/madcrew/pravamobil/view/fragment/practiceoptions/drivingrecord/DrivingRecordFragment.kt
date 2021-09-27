package com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.InstructorDatesRecyclerAdapter
import com.madcrew.pravamobil.databinding.FragmentDrivingRecordBinding
import com.madcrew.pravamobil.models.InstructorSpinnerItem
import com.madcrew.pravamobil.adapter.SpinnerInstructorAdapter
import com.madcrew.pravamobil.models.InstructorDateData


class DrivingRecordFragment : Fragment(), AdapterView.OnItemSelectedListener, InstructorDatesRecyclerAdapter.OnDateClickListener  {

    private var _binding: FragmentDrivingRecordBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: InstructorDatesRecyclerAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerInstructors = binding.drivingRecordInstructorSpinner
        val timesRecycler = binding.fragmentDrivingRecordInstructorTimeRecycler

        val instructors = mutableListOf(
            InstructorSpinnerItem("Анджелина Джоли", R.drawable.ic_woman),
            InstructorSpinnerItem("Бред Питт", R.drawable.ic_man)
        )

        val customAdapter =
            SpinnerInstructorAdapter(requireContext(), instructors)
        spinnerInstructors.adapter = customAdapter

        spinnerInstructors.onItemSelectedListener = this

        val mDateList = mutableListOf(
            InstructorDateData("10:00 - 11:00"),
            InstructorDateData("11:00 - 12:00"),
        )

        mAdapter = InstructorDatesRecyclerAdapter(mDateList, this)
        mAdapter.notifyDataSetChanged()

        timesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        mAdapter.notifyDataSetChanged()


        binding.btDrivingRecordEndRecord.setOnClickListener {
            this.activity?.finish()
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onDateClick(v: Button?) {

    }


}
package com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.InstructorDatesRecyclerAdapter
import com.madcrew.pravamobil.adapter.SpinnerInstructorAdapter
import com.madcrew.pravamobil.databinding.FragmentDrivingRecordBinding
import com.madcrew.pravamobil.models.InstructorDateData
import com.madcrew.pravamobil.models.InstructorSpinnerItem
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.LessonHistoryFragment
import java.util.*


class DrivingRecordFragment : Fragment(), AdapterView.OnItemSelectedListener,
    InstructorDatesRecyclerAdapter.OnDateClickListener {

    private var _binding: FragmentDrivingRecordBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: InstructorDatesRecyclerAdapter
    lateinit var selectedDate: String
    lateinit var selectedDayOfWeek: String
    lateinit var selectedTime: String
    lateinit var requestDate: String

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

        val calendar = binding.calendarView

        val today = Calendar.getInstance()

        val dayOfWeek = today.get(Calendar.DAY_OF_WEEK)
        selectedDayOfWeek =
            when (dayOfWeek) {
                Calendar.MONDAY -> resources.getString(R.string.monday)
                Calendar.TUESDAY -> resources.getString(R.string.tuesday)
                Calendar.WEDNESDAY -> resources.getString(R.string.wednesday)
                Calendar.THURSDAY -> resources.getString(R.string.thursday)
                Calendar.FRIDAY -> resources.getString(R.string.friday)
                Calendar.SATURDAY -> resources.getString(R.string.saturday)
                Calendar.SUNDAY -> resources.getString(R.string.saturday)
                else -> "wrong"
            }

        val daysMax = when (dayOfWeek) {
            Calendar.MONDAY -> 6 + 21
            Calendar.TUESDAY -> 5 + 21
            Calendar.WEDNESDAY -> 4 + 21
            Calendar.THURSDAY -> 3 + 21
            Calendar.FRIDAY -> 2 + 21
            Calendar.SATURDAY -> 1 + 21
            Calendar.SUNDAY -> 0 + 21
            else -> 0
        }

        val lastDay = today.clone() as Calendar
        lastDay.add(Calendar.DATE, daysMax)
        calendar.minDate = today.timeInMillis
        calendar.maxDate = lastDay.timeInMillis

        val maxDate = (Calendar.getInstance().clone() as Calendar)
        maxDate.add(Calendar.DATE, daysMax + 1)

        val maxDay = if (maxDate.get(Calendar.DAY_OF_MONTH).toString().length < 2) {
            "0${maxDate.get(Calendar.DAY_OF_MONTH)}"
        } else {
            maxDate.get(Calendar.DAY_OF_MONTH).toString()
        }
        val maxMonth =
            if ((maxDate.get(Calendar.MONTH) + 1).toString().length < 2) {
                "0${maxDate.get(Calendar.MONTH) + 1}"
            } else {
                maxDate.get(Calendar.MONTH) + 1
            }

        val maxSelectedDate = "$maxDay.$maxMonth.${today.get(Calendar.YEAR)}"

        val currentDay = if (today.get(Calendar.DAY_OF_MONTH).toString().length < 2) {
            "0${today.get(Calendar.DAY_OF_MONTH)}"
        } else {
            today.get(Calendar.DAY_OF_MONTH).toString()
        }
        val currentMonth =
            if ((today.get(Calendar.MONTH) + 1).toString().length < 2) {
                "0${today.get(Calendar.MONTH) + 1}"
            } else {
                today.get(Calendar.MONTH) + 1
            }

        selectedDate = "$currentDay.$currentMonth.${today.get(Calendar.YEAR)}"
        Log.i("selectedDate", selectedDate)

        val selectedDayWeek = today.get(Calendar.DAY_OF_WEEK)
        selectedDayOfWeek =
            when (selectedDayWeek) {
                Calendar.MONDAY -> resources.getString(R.string.monday)
                Calendar.TUESDAY -> resources.getString(R.string.tuesday)
                Calendar.WEDNESDAY -> resources.getString(R.string.wednesday)
                Calendar.THURSDAY -> resources.getString(R.string.thursday)
                Calendar.FRIDAY -> resources.getString(R.string.friday)
                Calendar.SATURDAY -> resources.getString(R.string.saturday)
                Calendar.SUNDAY -> resources.getString(R.string.sunday)
                else -> "wrong"
            }
        binding.drivingRecordSelectedDate.text = dateConverterForTitle(selectedDate, requireContext()) + " ($selectedDayOfWeek)"


        val calendarView = binding.drivingRecordCalendarCard
        calendarView.setGone()

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
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        mAdapter.notifyDataSetChanged()


        binding.btDrivingRecordEndRecord.setOnClickListener {
            this.activity?.finish()
        }

        binding.btDrivingRecordIcCalendar.setOnClickListener {
            calendarView.alphaUp(300)
            calendarView.setVisible()
        }

        binding.btDrivingRecordCalendarSelect.setOnClickListener {
            calendarView.alphaDown(300)
            calendarView.setGone()
            val selectedDayWeek = today.get(Calendar.DAY_OF_WEEK)
            selectedDayOfWeek =
                when (selectedDayWeek) {
                    Calendar.MONDAY -> resources.getString(R.string.monday)
                    Calendar.TUESDAY -> resources.getString(R.string.tuesday)
                    Calendar.WEDNESDAY -> resources.getString(R.string.wednesday)
                    Calendar.THURSDAY -> resources.getString(R.string.thursday)
                    Calendar.FRIDAY -> resources.getString(R.string.friday)
                    Calendar.SATURDAY -> resources.getString(R.string.saturday)
                    Calendar.SUNDAY -> resources.getString(R.string.sunday)
                    else -> "wrong"
                }
            binding.drivingRecordSelectedDate.text = dateConverterForTitle(selectedDate, requireContext()) + " ($selectedDayOfWeek)"
        }

        binding.btDrivingRecordNextDay.setOnClickListener {
            today.add(Calendar.DAY_OF_MONTH, 1)
            val incCal = today.time
            Log.i("incremented", incCal.toString())
            val curDay = if (today.get(Calendar.DAY_OF_MONTH).toString().length < 2) {
                "0${today.get(Calendar.DAY_OF_MONTH)}"
            } else {
                today.get(Calendar.DAY_OF_MONTH).toString()
            }
            val curMonth =
                if ((today.get(Calendar.MONTH) + 1).toString().length < 2) {
                    "0${today.get(Calendar.MONTH) + 1}"
                } else {
                    today.get(Calendar.MONTH) + 1
                }

            val selectedDayWeek = today.get(Calendar.DAY_OF_WEEK)
            selectedDayOfWeek =
                when (selectedDayWeek) {
                    Calendar.MONDAY -> resources.getString(R.string.monday)
                    Calendar.TUESDAY -> resources.getString(R.string.tuesday)
                    Calendar.WEDNESDAY -> resources.getString(R.string.wednesday)
                    Calendar.THURSDAY -> resources.getString(R.string.thursday)
                    Calendar.FRIDAY -> resources.getString(R.string.friday)
                    Calendar.SATURDAY -> resources.getString(R.string.saturday)
                    Calendar.SUNDAY -> resources.getString(R.string.sunday)
                    else -> "wrong"
                }

            selectedDate = "$curDay.$curMonth.${today.get(Calendar.YEAR)}"
            if (selectedDate == maxSelectedDate){
                today.add(Calendar.DAY_OF_MONTH, -1)
            } else {
                binding.drivingRecordSelectedDate.text = dateConverterForTitle(selectedDate, requireContext()) + " ($selectedDayOfWeek)"
            }
            Log.i("daysSelect", "$selectedDate , $maxSelectedDate")
        }

        calendar.setOnDateChangeListener { view, year, month, day ->
            val selectedDay =
                if (day.toString().length < 2) {
                    "0$day"
                } else {
                    day
                }
            val selectedMonth =
                if ((month + 1).toString().length < 2) {
                    "0${month + 1}"
                } else {
                    month + 1
                }
            val date = "$selectedDay.$selectedMonth.$year"
            selectedDate = date
            Log.i("selectedDate", selectedDate)
            Log.i("dayOfMonth", day.toString())
            today.set(year, month, day)

        }

        binding.btDrivingRecordLessonHistory.setOnClickListener {
            val mainManager = parentFragmentManager
            val transaction: FragmentTransaction = mainManager.beginTransaction()
            transaction.replace(R.id.practice_options_fragment_container, LessonHistoryFragment(0))
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            transaction.commit()
        }
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onDateClick(v: Button?) {

    }


}
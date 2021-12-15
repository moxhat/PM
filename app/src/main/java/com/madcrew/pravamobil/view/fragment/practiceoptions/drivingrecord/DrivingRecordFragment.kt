package com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord

import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.InstructorDatesRecyclerAdapter
import com.madcrew.pravamobil.adapter.SpinnerInstructorAdapter
import com.madcrew.pravamobil.databinding.FragmentDrivingRecordBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.InstructorDateData
import com.madcrew.pravamobil.models.InstructorSpinnerItem
import com.madcrew.pravamobil.models.requestmodels.AvailableTimesRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.models.requestmodels.WriteToLessonRequest
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.enter.EnterActivity
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionsActivity
import com.madcrew.pravamobil.view.dialog.ConfirmRecordDialogFragment
import com.madcrew.pravamobil.view.dialog.RecordAddedDialogFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.LessonHistoryFragment
import java.util.*


class DrivingRecordFragment : Fragment(), AdapterView.OnItemSelectedListener,
    InstructorDatesRecyclerAdapter.OnDateClickListener {

    private var _binding: FragmentDrivingRecordBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: InstructorDatesRecyclerAdapter
    private lateinit var mViewModel: DrivingRecordViewModel
    lateinit var selectedDate: String
    lateinit var selectedDayOfWeek: String
    lateinit var mDateList: MutableList<InstructorDateData>
    lateinit var instructors: MutableList<InstructorSpinnerItem>
    lateinit var instructorID: String
    private var selectedTimePosition = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrivingRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parent = this.context as PracticeOptionsActivity

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        val repository = Repository()
        val viewModelFactory = DrivingRecordViewModelFactory(repository)
        mViewModel =
            ViewModelProvider(this, viewModelFactory).get(DrivingRecordViewModel::class.java)

        mViewModel.instructorsResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                when (response.body()!!.status) {
                    "done" -> {
                        val tmpInstructors = mutableListOf<InstructorSpinnerItem>()
                        for (i in response.body()?.instructors!!) {
                            tmpInstructors.add(
                                InstructorSpinnerItem(
                                    "${i.secondName} ${i.name?.get(0)}. ${
                                        i.patronymic?.get(
                                            0
                                        )
                                    }.", i.photoUrl.toString()
                                )
                            )
                        }
                        if (response.body()!!.instructors?.size == 1) {
                            instructorID = response.body()!!.instructors?.get(0)?.id!!
                        }
                        instructors = tmpInstructors
                        setUpInstructorsSpinner()
                        getAvailableTimesSelectedDay()
                    }
                    "error" -> Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.no_instructors),
                        Toast.LENGTH_SHORT
                    ).show()
                    "fail" -> Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.no_client),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                showServerError(requireContext())
            }
        })

        mViewModel.availableTimes.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                if (response.body()!!.status == "done") {
                    if (response.body()!!.times?.size == 0) {
                        binding.drivingRecordAvailableTimeTitle.setText(R.string.no_available_time)
                        mDateList = mutableListOf()
                        mDateList.clear()
                    } else {
                        binding.drivingRecordAvailableTimeTitle.setText(R.string.available_time)
                        val tmpDateList = mutableListOf<InstructorDateData>()
                        for (i in response.body()!!.times!!) {
                            tmpDateList.add(InstructorDateData(i.title))
                        }
                        mDateList = tmpDateList
                    }
                    setupTimeRecycler()
                }
            }
        })

        mViewModel.writingResponse.observe(viewLifecycleOwner, {response ->
            if (response.isSuccessful){
                when (response.body()!!.status ){
                    "done" -> {
                        val confirmedDialog = RecordAddedDialogFragment()
                        confirmedDialog.show(parentFragmentManager, "RecordAddedDialogFragment")
                        mDateList.removeAt(selectedTimePosition)
                        mAdapter.notifyItemRemoved(selectedTimePosition)
                        if (mDateList.size == 0){
                            binding.drivingRecordAvailableTimeTitle.setText(R.string.no_available_time)
                        }
                    }
                    "error" -> {
                        Toast.makeText(requireContext(), response.body()!!.error, Toast.LENGTH_SHORT).show()
                    }
                    "fail" -> {
                        Toast.makeText(requireContext(), resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        val calendar = binding.calendarView

        val today = Calendar.getInstance()

        val dayOfWeek = today.get(Calendar.DAY_OF_WEEK)
        selectedDofW(dayOfWeek)

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
        selectedDofW(selectedDayWeek)
        binding.drivingRecordSelectedDate.text =
            dateConverterForTitle(selectedDate, requireContext()) + " ($selectedDayOfWeek)"

        if (isOnline(requireContext())) {
            mViewModel.getInstructors(SpravkaStatusRequest(TOKEN, schoolId, clientId))
        } else {
            noInternet(requireContext())
        }


        val calendarView = binding.drivingRecordCalendarCard
        calendarView.setGone()

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
            selectedDofW(selectedDayWeek)
            binding.drivingRecordSelectedDate.text =
                dateConverterForTitle(selectedDate, requireContext()) + " " + selectedDayOfWeek
            getAvailableTimesSelectedDay()
        }

        binding.btDrivingRecordNextDay.setOnClickListener {
            today.add(Calendar.DAY_OF_MONTH, 1)
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
            selectedDofW(selectedDayWeek)

            selectedDate = "$curDay.$curMonth.${today.get(Calendar.YEAR)}"
            if (selectedDate == maxSelectedDate) {
                today.add(Calendar.DAY_OF_MONTH, -1)
            } else {
                binding.drivingRecordSelectedDate.text =
                    dateConverterForTitle(selectedDate, requireContext()) + " " + selectedDayOfWeek
            }
            getAvailableTimesSelectedDay()
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
            today.set(year, month, day)
        }

        binding.btDrivingRecordLessonHistory.setOnClickListener {
            val mainManager = parentFragmentManager
            val transaction: FragmentTransaction = mainManager.beginTransaction()
            transaction.replace(R.id.practice_options_fragment_container, LessonHistoryFragment("theory",0))
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            transaction.commit()
        }
    }

    private fun setUpInstructorsSpinner() {
        val spinnerInstructors = binding.drivingRecordInstructorSpinner
        val customAdapter =
            SpinnerInstructorAdapter(requireContext(), instructors)
        spinnerInstructors.adapter = customAdapter
        spinnerInstructors.isEnabled = instructors.size != 1
        spinnerInstructors.onItemSelectedListener = this
    }

    private fun setupTimeRecycler() {
        val timesRecycler = binding.fragmentDrivingRecordInstructorTimeRecycler
        mAdapter = InstructorDatesRecyclerAdapter(mDateList, this)
        timesRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        mAdapter.notifyDataSetChanged()
    }

    private fun selectedDofW(selectedDayWeek: Int) {
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
    }

    fun getAvailableTimesSelectedDay() {
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
        mViewModel.getAvailableTimes(
            AvailableTimesRequest(
                TOKEN,
                schoolId,
                instructorID,
                selectedDate
            )
        )
    }

    fun confirmWriteToLesson(timeId: String, timeTitle: String){
        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
        mViewModel.writeToLesson(WriteToLessonRequest(TOKEN, schoolId, instructorID, clientId, selectedDate, timeId, timeTitle))
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        instructorID =
            mViewModel.instructorsResponse.value?.body()!!.instructors?.get(p2)?.id.toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onDateClick(v: Button?, position: Int) {
        selectedTimePosition = position
        val date = "${dateConverter(selectedDate, requireContext())} ${mDateList[position].date}"
        val name =
            instructors[binding.drivingRecordInstructorSpinner.selectedItemPosition].instructorName
        val timeTitle = mDateList[position].date
        val timeId = mViewModel.availableTimes.value!!.body()!!.times?.get(position)?.id.toString()
        val confirmDialog = ConfirmRecordDialogFragment(date, name, timeId, timeTitle)
        confirmDialog.show(childFragmentManager, "ConfirmRecordDialogFragment")
    }


}
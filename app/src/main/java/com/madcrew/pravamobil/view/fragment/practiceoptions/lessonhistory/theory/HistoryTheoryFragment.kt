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
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.LessonsData
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.dateConverterForTitle
import com.madcrew.pravamobil.utils.showServerError
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionsActivity


class HistoryTheoryFragment : Fragment(), LessonHistoryRecyclerAdapter.OnStatusClickListener {

    private var _binding: FragmentHistoryTheoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAdapter: LessonHistoryRecyclerAdapter
    private  var mLessonsList = mutableListOf<LessonsData>()

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

        val parent = this.context as PracticeOptionsActivity

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        parent.mViewModel.getTheoryHistory(SpravkaStatusRequest(TOKEN, schoolId, clientId))

        parent.mViewModel.theoryHistory.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful){
                if (response.body()!!.status == "done"){
                    mLessonsList.clear()
                    for (i in response.body()!!.schedule!!){
                        val name = "${i.secondName} ${i.name!![0]}. ${i.patronymic!![0]}"
                        mLessonsList.add(LessonsData(dateConverterForTitle(i.date.toString(), requireContext()), name, i.status, 1.0))
                    }
                    mAdapter = LessonHistoryRecyclerAdapter(mLessonsList, this)
                    binding.historyTheoryRecycler.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = mAdapter
                    }
                    mAdapter.notifyDataSetChanged()
                } else {
                    showServerError(requireContext())
                }
            } else {
                showServerError(requireContext())
            }
        })
    }

    override fun onStatusClick(itemView: View?, position: Int) {
        Toast.makeText(requireContext(), "Теория", Toast.LENGTH_SHORT).show()
    }
}
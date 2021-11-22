package com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.LessonHistoryPagerAdapter
import com.madcrew.pravamobil.databinding.FragmentLessonHistoryBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.setInvisible
import com.madcrew.pravamobil.utils.setVisible
import com.madcrew.pravamobil.view.fragment.education.home.HomeViewModel
import com.madcrew.pravamobil.view.fragment.education.home.HomeViewModelFactory
import com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord.DrivingRecordFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.practice.HistoryPracticeFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.theory.HistoryTheoryFragment


class LessonHistoryFragment(var trigger: Int) : Fragment() {

    private var _binding: FragmentLessonHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyViewPager: ViewPager2
    private lateinit var historyPagerAdapter: LessonHistoryPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLessonHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lessonCancelTitle.setInvisible()

        historyViewPager = binding.lessonHistoryViewPager

        val historyFragmentsList = mutableListOf(HistoryTheoryFragment(), HistoryPracticeFragment())
        historyPagerAdapter = LessonHistoryPagerAdapter(this, historyFragmentsList.toMutableList())
        historyViewPager.adapter = historyPagerAdapter
        historyViewPager.currentItem = 0

        val tabLayout = binding.lessonHistoryTabs
        TabLayoutMediator(tabLayout, historyViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.theory)
                1 -> tab.text = resources.getString(R.string.practice)
            }
        }.attach()

        historyViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0){
                    binding.lessonCancelTitle.setInvisible()
                } else {
                    binding.lessonCancelTitle.setVisible()
                }
            }
        })

        binding.btLessonCancelBack.setOnClickListener {
            if (trigger == 1) {
                this.activity?.finish()
            } else {
                val mainManager = parentFragmentManager
                val transaction: FragmentTransaction = mainManager.beginTransaction()
                transaction.replace(R.id.practice_options_fragment_container, DrivingRecordFragment())
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                transaction.commit()
            }
        }
    }
}
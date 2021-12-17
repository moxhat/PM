package com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.LessonHistoryPagerAdapter
import com.madcrew.pravamobil.databinding.FragmentLessonHistoryBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.utils.setInvisible
import com.madcrew.pravamobil.utils.setVisible
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionsActivity
import com.madcrew.pravamobil.view.dialog.SpravkaConfirmedDialogFragment
import com.madcrew.pravamobil.view.fragment.education.home.HomeViewModel
import com.madcrew.pravamobil.view.fragment.education.home.HomeViewModelFactory
import com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord.DrivingRecordFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.practice.HistoryPracticeFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.theory.HistoryTheoryFragment


class LessonHistoryFragment(var from: String, var trigger: Int) : Fragment() {

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

        val tabLayout = binding.lessonHistoryTabs



        when (from){
            "theory" -> historyViewPager.currentItem = 0
            "practice" -> historyViewPager.currentItem = 1
        }

        when (Preferences.getPrefsString("spravkaStatus", requireContext())) {
            "confirm" -> {
                val historyFragmentsList = mutableListOf(HistoryTheoryFragment(), HistoryPracticeFragment())
                historyPagerAdapter = LessonHistoryPagerAdapter(this, historyFragmentsList.toMutableList())
                historyViewPager.adapter = historyPagerAdapter

                TabLayoutMediator(tabLayout, historyViewPager) { tab, position ->
                    when (position) {
                        0 -> tab.text = resources.getString(R.string.theory)
                        1 -> tab.text = resources.getString(R.string.practice)
                    }
                }.attach()
            }
            else -> {
                val historyFragmentsList = mutableListOf(HistoryTheoryFragment())
                historyPagerAdapter = LessonHistoryPagerAdapter(this, historyFragmentsList.toMutableList())
                historyViewPager.adapter = historyPagerAdapter
                tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.theory)))
                tabLayout.tabMode = TabLayout.MODE_FIXED
                historyViewPager.isUserInputEnabled = false
            }
        }


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
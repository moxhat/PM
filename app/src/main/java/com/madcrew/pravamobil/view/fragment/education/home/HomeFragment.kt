package com.madcrew.pravamobil.view.fragment.education.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.HomePagerAdapter
import com.madcrew.pravamobil.databinding.FragmentHomeBinding
import com.madcrew.pravamobil.view.dialog.SpravkaConfirmedDialogFragment
import com.madcrew.pravamobil.view.dialog.SpravkaImageDialogFragment
import com.madcrew.pravamobil.view.fragment.education.home.exam.HomeExamFragment
import com.madcrew.pravamobil.view.fragment.education.home.practice.HomePracticeFragment
import com.madcrew.pravamobil.view.fragment.education.home.practice.beforespravka.ConfirmationSpravkaFragment
import com.madcrew.pravamobil.view.fragment.education.home.practice.beforespravka.NoSpravkaFragment
import com.madcrew.pravamobil.view.fragment.education.home.theory.HomeTheoryFragment
import okhttp3.internal.notifyAll

class HomeFragment : Fragment() {

    var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private lateinit var homeViewPager: ViewPager2
    private lateinit var homePagerAdapter: HomePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewPager = binding.homeViewPager


        setSpravkaAdd()
        homeViewPager.adapter = homePagerAdapter
        homeViewPager.currentItem = 0

//        Handler(Looper.getMainLooper()).postDelayed({
//            setSpravkaConfirmation()
//            val item = homeViewPager.currentItem
//            homeViewPager.adapter = homePagerAdapter
//            homeViewPager.currentItem = item
//
//        }, 5000)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//
//
//        }, 10000)

        setSpravkaConfirmed()
        val item = homeViewPager.currentItem
        homeViewPager.adapter = homePagerAdapter
        homeViewPager.currentItem = item



        val tabLayout = binding.homeTabs
        TabLayoutMediator(tabLayout, homeViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.theory)
                1 -> tab.text = resources.getString(R.string.practice)
                2 -> tab.text = resources.getString(R.string.exam)
            }
        }.attach()

    }

    fun setSpravkaConfirmed() {
        val fragmentsWithSpravkaList = mutableListOf(HomeTheoryFragment(), HomePracticeFragment(), HomeExamFragment())
        homePagerAdapter = HomePagerAdapter(this, fragmentsWithSpravkaList)
    }

    fun setSpravkaConfirmation() {
        val fragmentsConfirmationSpravkaList = mutableListOf(HomeTheoryFragment(), ConfirmationSpravkaFragment(), HomeExamFragment())
        homePagerAdapter = HomePagerAdapter(this, fragmentsConfirmationSpravkaList)
    }

    fun setSpravkaAdd() {
        val fragmentsNoSpravkaList = mutableListOf(HomeTheoryFragment(), NoSpravkaFragment(), HomeExamFragment())
        homePagerAdapter = HomePagerAdapter(this, fragmentsNoSpravkaList)
    }

}
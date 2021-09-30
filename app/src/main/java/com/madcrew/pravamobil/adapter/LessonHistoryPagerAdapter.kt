package com.madcrew.pravamobil.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.madcrew.pravamobil.view.fragment.education.home.exam.HomeExamFragment

class LessonHistoryPagerAdapter(fragment: Fragment, var fragmentList: MutableList<Any>) : FragmentStateAdapter(fragment) {
    private val NUM_PAGES = 2

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            position -> fragmentList[position] as Fragment
            else -> HomeExamFragment()
        }
    }

}
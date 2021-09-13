package com.madcrew.pravamobil.adapter

import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.madcrew.pravamobil.view.fragment.education.home.exam.HomeExamFragment
import com.madcrew.pravamobil.view.fragment.education.home.practice.HomePracticeFragment
import com.madcrew.pravamobil.view.fragment.education.home.theory.HomeTheoryFragment

class HomePagerAdapter(fragment: Fragment, var fragmentList: MutableList<Fragment>) : FragmentStateAdapter(fragment) {
    private val NUM_PAGES = 3

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            position -> fragmentList[position]
            else -> HomeExamFragment()
        }
    }

}
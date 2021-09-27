package com.madcrew.pravamobil.view.activity.practiceoptions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.ActivityPracticeOptionsBinding
import com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord.DrivingRecordFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.LessonHistoryFragment
import com.madcrew.pravamobil.view.fragment.progress.addpassword.AddPasswordFragment

class PracticeOptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPracticeOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPracticeOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = when (intent.extras?.getString("option")) {
            "record" -> DrivingRecordFragment()
            "history" -> LessonHistoryFragment()
            else -> DrivingRecordFragment()
        }

        setUpFragment(currentFragment)

    }

    private fun setUpFragment(fragment: Fragment){
        val mainManager = supportFragmentManager
        val transaction: FragmentTransaction = mainManager.beginTransaction()
        transaction.replace(R.id.practice_options_fragment_container, fragment)
        transaction.commit()
    }
}
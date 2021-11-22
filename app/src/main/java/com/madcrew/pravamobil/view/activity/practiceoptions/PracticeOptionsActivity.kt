package com.madcrew.pravamobil.view.activity.practiceoptions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.ActivityPracticeOptionsBinding
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.view.fragment.education.home.HomeViewModel
import com.madcrew.pravamobil.view.fragment.education.home.HomeViewModelFactory
import com.madcrew.pravamobil.view.fragment.practiceoptions.drivingrecord.DrivingRecordFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.LessonHistoryFragment
import com.madcrew.pravamobil.view.fragment.practiceoptions.lessonhistory.openlesson.OpenLessonFragment
import com.madcrew.pravamobil.view.fragment.progress.addpassword.AddPasswordFragment
import com.skydoves.powerspinner.createPowerSpinnerView
import java.text.FieldPosition

class PracticeOptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPracticeOptionsBinding
    lateinit var mViewModel: PracticeOptionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPracticeOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = PracticeOptionViewModelFactory(repository)
        mViewModel = ViewModelProvider(this, viewModelFactory).get(PracticeOptionViewModel::class.java)

        val currentFragment = when (intent.extras?.getString("option")) {
            "record" -> DrivingRecordFragment()
            "history" -> LessonHistoryFragment(1)
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

    fun addOpenLesson(status: String, rating: Double, position: Int){
        val mainManager = supportFragmentManager
        val transaction: FragmentTransaction = mainManager.beginTransaction()
        transaction.addToBackStack("history")
        transaction.add(R.id.practice_options_fragment_container, OpenLessonFragment(status, rating, position))
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        transaction.commit()
    }
}
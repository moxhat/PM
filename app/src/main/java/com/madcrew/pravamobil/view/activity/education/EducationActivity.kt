package com.madcrew.pravamobil.view.activity.education

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.ActivityEducationBinding
import com.madcrew.pravamobil.view.dialog.EducationStartsDialogFragment
import com.madcrew.pravamobil.view.fragment.education.home.HomeFragment
import com.madcrew.pravamobil.view.fragment.education.payments.PaymentsFragment


class EducationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEducationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomMenu = binding.educationBottomNavigation

        bottomMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.education_home -> {
                    changeFragment(supportFragmentManager, HomeFragment(), "HomeFragment")
                }
                R.id.education_payments -> {
                    changeFragment(supportFragmentManager, PaymentsFragment(), "PaymentsFragment")
                }
                R.id.education_more -> {
                    Toast.makeText(this, "Later!", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        bottomMenu.selectedItemId = R.id.education_home

        Handler(Looper.getMainLooper()).postDelayed({
            val greetingsFragment = EducationStartsDialogFragment()
            greetingsFragment.show(supportFragmentManager, "EducationStartsDialogFragment")
        },1000)
    }

    private fun changeFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        tag: String
    ) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.apply {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            replace(R.id.education_fragment_container, fragment, tag)
            commit()
        }
    }
}
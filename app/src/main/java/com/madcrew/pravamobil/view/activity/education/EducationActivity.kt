package com.madcrew.pravamobil.view.activity.education

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.ActivityEducationBinding
import com.madcrew.pravamobil.domain.BaseUrl
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.requestmodels.ProgressRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.enter.EnterActivity
import com.madcrew.pravamobil.view.activity.paymentsoptions.PaymentsOptionsActivity
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionsActivity
import com.madcrew.pravamobil.view.dialog.EducationStartsDialogFragment
import com.madcrew.pravamobil.view.fragment.education.home.HomeFragment
import com.madcrew.pravamobil.view.fragment.education.payments.PaymentsFragment


class EducationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEducationBinding
    lateinit var mViewModel: EducationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.educationMoreMenuConstraint.setGone()

        val bottomMenu = binding.educationBottomNavigation
        var lastSelected = 0

        val repository = Repository()
        val viewModelFactory = EducationViewModelFactory(repository)
        mViewModel = ViewModelProvider(this, viewModelFactory).get(EducationViewModel::class.java)

        val clientId = Preferences.getPrefsString("clientId", this).toString()
        val schoolId = Preferences.getPrefsString("schoolId", this).toString()

        mViewModel.updateProgress(ProgressRequest(TOKEN, schoolId, clientId, "AppHome"))
        bottomMenu.menu.getItem(2).isCheckable = false
//        bottomMenu.menu.getItem(1).isEnabled = false

        // Временно
        binding.btEducationMoreMenuNews.setDisable()
        binding.btEducationMoreMenuProfile.setDisable()
        binding.btEducationMoreMenuAbout.setDisable()
        binding.btEducationMoreMenuRateApp.setDisable()


        bottomMenu.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.education_home -> {
                    changeFragment(supportFragmentManager, HomeFragment(), "HomeFragment")
                    lastSelected = 0
                    setStatusBarColorBlue()
                }
                R.id.education_payments -> {
                    changeFragment(supportFragmentManager, PaymentsFragment(), "PaymentsFragment")
                    lastSelected = 1
                }
                R.id.education_more -> {
                    showMenu(binding.educationMoreMenuConstraint)
                }
            }
            true
        }

        bottomMenu.selectedItemId = R.id.education_home

        if (Preferences.getPrefsString("EducationStartsDialogFragment", this) != "1"){
            Handler(Looper.getMainLooper()).postDelayed({
                val greetingsFragment = EducationStartsDialogFragment()
                greetingsFragment.show(supportFragmentManager, "EducationStartsDialogFragment")
            },1000)
        }

        binding.educationMoreMenuConstraint.setOnClickListener {
            hideMenu(it)
            val lastItem = bottomMenu.menu.getItem(lastSelected)
            lastItem.setChecked(true)
        }

        binding.btEducationMoreMenuSignOut.setOnClickListener{
            Preferences.setPrefsString("rememberMe", "false", this)
            startEnterActivity()
            finish()
        }
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

    private fun showMenu(view: View){
        view.setVisible()
        view.alphaUp(100)
    }

    private fun hideMenu(view: View){
        view.setGone()
        view.alphaDown(100)
    }

    fun starPracticeOptionsActivity(from: String, option: String) {
        val intent = Intent(this, PracticeOptionsActivity::class.java)
        intent.putExtra("from", from)
        intent.putExtra("option", option)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    fun setStatusBarColorBlue(){
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.main)
    }

    fun setStatusBarColorRed(){
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.red_alert)
    }

    fun updateClientData(data: FullRegistrationRequest){
        if (isOnline(this)) {
            mViewModel.updateClientData(data)
        } else {
            noInternet(this)
        }
    }

    fun starPaymentsOption(type: String, url: String = "empty") {
        val intent = Intent(this, PaymentsOptionsActivity::class.java)
        intent.putExtra("url", url)
        intent.putExtra("type", type)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun startEnterActivity() {
        val intent = Intent(this, EnterActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

}
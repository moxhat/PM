package com.madcrew.pravamobil.view.activity.paymentsoptions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.ActivityPaymentsOptionsBinding
import com.madcrew.pravamobil.databinding.ActivityPracticeOptionsBinding
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionViewModel
import com.madcrew.pravamobil.view.activity.practiceoptions.PracticeOptionViewModelFactory
import com.madcrew.pravamobil.view.fragment.education.payments.paymentshistory.PaymentsHistoryFragment
import com.madcrew.pravamobil.view.fragment.progress.PaymentWebViewFragment

class PaymentsOptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentsOptionsBinding
    lateinit var mViewModel: PaymentsOptionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentsOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = PaymentsOptionsViewModelFactory(repository)
        mViewModel = ViewModelProvider(this, viewModelFactory).get(PaymentsOptionsViewModel::class.java)

        val clientId = Preferences.getPrefsString("clientId", this).toString()
        val schoolId = Preferences.getPrefsString("schoolId", this).toString()

        val type = intent.getStringExtra("type")
        val url = intent.getStringExtra("url").toString()
        when(type){
            "paymentNearest" -> setupFragment(PaymentWebViewFragment(false, url))
            "paymentAll" -> PaymentWebViewFragment(false, url)
            "additional" -> PaymentWebViewFragment(false, url)
            "history" -> PaymentsHistoryFragment()
        }
    }

    private fun setupFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
        setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            replace(R.id.payments_option_fragment_container, fragment)
            commit()
        }
    }
}
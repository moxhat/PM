package com.madcrew.pravamobil.view.activity.progress

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.ActivityProgressBinding
import com.madcrew.pravamobil.view.fragment.progress.addpassword.AddPasswordFragment

class ProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.main)

        val mainManager = supportFragmentManager
        val transaction: FragmentTransaction = mainManager.beginTransaction()
        transaction.replace(R.id.progress_activity_fragment_container, AddPasswordFragment())
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        transaction.commit()
    }
}
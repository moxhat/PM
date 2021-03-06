package com.madcrew.pravamobil.view.activity.splash

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.ActivitySplashBinding
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.isOnline
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.utils.setVisible
import com.madcrew.pravamobil.view.activity.enter.EnterActivity
import java.util.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // заливка статус бара черным цветом
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)

        val webView = binding.spalshAnimationWebview
        val bgImage = binding.splashBgImage

        val devId = UUID.randomUUID()
        if (!this.getSharedPreferences("appSettingsSingleOptions", MODE_PRIVATE).contains("deviceId")){
            Preferences.setPrefsString("deviceId", devId.toString(), this)
        }

        if (isOnline(this)){
            webView.setVisible()
            bgImage.setGone()

            val webSettings = webView.settings
            webSettings.allowContentAccess = true
            webSettings.allowFileAccess = true
            webSettings.useWideViewPort = true
            webSettings.loadWithOverviewMode = true
            webSettings.javaScriptEnabled = true
            webSettings.cacheMode
            webView.loadUrl("https://new.serviceavtoshkola.ru/bg_anim.svg")
            webView.setBackgroundColor(0x00000000)
        } else {
            webView.setGone()
            bgImage.setVisible()
        }



        Handler(Looper.getMainLooper()).postDelayed({
            starProgressActivity()
        }, 2500)
    }

    private fun starProgressActivity() {
        val intent = Intent(this, EnterActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}
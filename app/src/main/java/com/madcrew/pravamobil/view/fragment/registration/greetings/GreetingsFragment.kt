package com.madcrew.pravamobil.view.fragment.registration.greetings

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentGreetingsBinding
import com.madcrew.pravamobil.utils.isOnline
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.utils.setVisible
import com.madcrew.pravamobil.view.activity.enter.EnterActivity
import java.util.*

class GreetingsFragment : Fragment() {

    private var _binding: FragmentGreetingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingsBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView = binding.greetingsAnimationWebview
        val bgImage = binding.greetingsBgImage
        val greetingTitle = binding.greetingsTitle1

        val parent = this.context as EnterActivity

        val cal = Calendar.getInstance()
        when (cal.get(Calendar.HOUR_OF_DAY)) {
            in 5..10 -> greetingTitle.setText(R.string.greetings_morning)
            in 11..19 -> greetingTitle.setText(R.string.greetings_day)
            in 20..23 -> greetingTitle.setText(R.string.greetings_evening)
            in 0..4 -> greetingTitle.setText(R.string.greetings_night)
        }

        if (isOnline(requireContext())){
            webView.setVisible()
            bgImage.setGone()

            val webSettings = webView.settings

            webSettings.apply {
                allowContentAccess = true
                allowFileAccess = true
                useWideViewPort = true
                loadWithOverviewMode = true
                javaScriptEnabled = true
                cacheMode
            }

            webView.loadUrl("https://new.serviceavtoshkola.ru/bg_anim.svg")
            webView.setBackgroundColor(0x00000000)
        } else {
            webView.setGone()
            bgImage.setVisible()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            parent.starProgressActivity()
        }, 3300)
    }
}
package com.madcrew.pravamobil.view.activity.practiceoptions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.madcrew.pravamobil.databinding.ActivityPracticeOptionsBinding

class PracticeOptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPracticeOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPracticeOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
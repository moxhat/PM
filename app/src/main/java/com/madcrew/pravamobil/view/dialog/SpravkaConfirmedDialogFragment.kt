package com.madcrew.pravamobil.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentSpravkaConfirmedDialogBinding
import com.madcrew.pravamobil.view.fragment.education.home.HomeFragment


class SpravkaConfirmedDialogFragment(var type: String) : DialogFragment() {

    private var _binding: FragmentSpravkaConfirmedDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpravkaConfirmedDialogBinding.inflate(inflater, container, false)
        if (!(dialog == null || dialog!!.window == null)) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (type){
            "good" -> {
                binding.spravkaConfirmedTitleImage.setImageResource(R.drawable.ic_shield)
                binding.spravkaConfirmedTitle.text = resources.getString(R.string.spravka_confirmed)
                binding.spravkaConfirmedText.text = resources.getString(R.string.spravka_confirmed_text)
            }
            "bad" -> {
                binding.spravkaConfirmedTitleImage.setImageResource(R.drawable.ic_spravka_bad)
                binding.spravkaConfirmedTitle.text = resources.getString(R.string.spravka_noconfirmed)
                binding.spravkaConfirmedText.text = resources.getString(R.string.spravka_noconfirmed_text)
            }
        }

        binding.btSpravkaConfirmedClose.setOnClickListener {

            this.dialog?.dismiss()
        }

    }
}
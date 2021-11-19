package com.madcrew.pravamobil.view.fragment.education.home.practice.beforespravka

import android.os.Bundle
import android.preference.Preference
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentNoSpravkaBinding
import com.madcrew.pravamobil.view.activity.education.EducationActivity
import com.madcrew.pravamobil.view.dialog.SpravkaDataDialogFragment
import com.madcrew.pravamobil.view.dialog.SpravkaImageDialogFragment
import com.madcrew.pravamobil.view.fragment.education.home.HomeFragment
import java.util.prefs.Preferences


class NoSpravkaFragment : Fragment() {

    private var _binding: FragmentNoSpravkaBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoSpravkaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btNoSpravkaAdd.setOnClickListener {
            val spravkaDataDialog = SpravkaDataDialogFragment()
            spravkaDataDialog.show(childFragmentManager, "SpravkaDataDialogFragment")
        }
    }

    fun showSpravkaImageDialog(){
        com.madcrew.pravamobil.utils.Preferences.setPrefsString("loadingSpravka","true", requireContext())
        val spravkaImageDialog = SpravkaImageDialogFragment()
        spravkaImageDialog.show(childFragmentManager, "SpravkaImageDialogFragment")
    }

    fun setSpravkaConfirmation(){
        val parent = this.context as EducationActivity
        val fm = parent.supportFragmentManager
        val fragm: HomeFragment = fm.findFragmentById(R.id.education_fragment_container) as HomeFragment
        fragm.setSpravkaConfirmation()
    }
}
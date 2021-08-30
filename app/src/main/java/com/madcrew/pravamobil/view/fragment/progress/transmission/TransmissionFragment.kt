package com.madcrew.pravamobil.view.fragment.progress.transmission

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentTransmissionBinding
import com.madcrew.pravamobil.view.dialog.InfoFragment
import com.madcrew.pravamobil.view.fragment.progress.selecttheory.SelectTheoryFragment


class TransmissionFragment(private var selectedCategory: String) : Fragment() {

    private var _binging:FragmentTransmissionBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentTransmissionBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.transmissionTitle.text = selectedCategory

        binding.btTransmissionMechanic.setOnClickListener {
            replaceFragment(SelectTheoryFragment(), R.anim.slide_left_in, R.anim.slide_left_out)
        }

        binding.btTransmissionAutomatic.setOnClickListener {
            replaceFragment(SelectTheoryFragment(), R.anim.slide_left_in, R.anim.slide_left_out)
        }

        binding.btTransmissionHelp.setOnClickListener {
            showInfo()
        }

    }

    private fun replaceFragment(fragment: Fragment, animationIn: Int, animationOut: Int){
        val mainManager = parentFragmentManager
        val transaction: FragmentTransaction = mainManager.beginTransaction()
        transaction.setCustomAnimations(animationIn, animationOut)
        transaction.remove(this)
        transaction.replace(R.id.progress_activity_fragment_container, fragment)
        transaction.commit()
    }

    private fun showInfo(){
        val newFragment =
            InfoFragment()
        newFragment.show(parentFragmentManager, "InfoFragment")
    }
}
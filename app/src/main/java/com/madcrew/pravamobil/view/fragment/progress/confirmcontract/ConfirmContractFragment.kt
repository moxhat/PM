package com.madcrew.pravamobil.view.fragment.progress.confirmcontract

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.madcrew.pravamobil.databinding.FragmentConfirmContractBinding
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.utils.setVisible
import com.madcrew.pravamobil.view.fragment.progress.ContractConfirmedFragment


class ConfirmContractFragment(var type: String) : Fragment() {

    private var _binding: FragmentConfirmContractBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmContractBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contractClientEmail.text = Preferences.getPrefsString("email", requireContext())

        val studentCheck = binding.contractStudentChek
        val parentCheck1 = binding.contractParentChek1
        val parentCheck2 = binding.contractParentChek2
        var parent1 = false
        var parent2 = false

        contractNotConfirmed()

        studentCheck.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) contractConfirmed()
        }

        parentCheck1.setOnCheckedChangeListener { _, isChecked ->
             if (isChecked && parent2) contractConfirmed() else contractNotConfirmed()
            parent1 = isChecked
        }

        parentCheck2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && parent1) contractConfirmed() else contractNotConfirmed()
            parent2 = isChecked
        }

        when (type){
            "student" -> {
                binding.contractStudentChek.setVisible()
                binding.contractParentChek1.setGone()
                binding.contractParentChek2.setGone()
            }
            "parent" -> {
                binding.contractStudentChek.setGone()
                binding.contractParentChek1.setVisible()
                binding.contractParentChek2.setVisible()
            }
        }

        binding.btContractNext.setOnClickListener {
            nextFragmentInProgress(parentFragmentManager, ContractConfirmedFragment())
        }
    }

    private fun contractConfirmed(){
        binding.btContractNext.apply {
            isEnabled = true
            alpha = 1f
        }

    }

    private fun contractNotConfirmed(){
        binding.btContractNext.apply {
            isEnabled = false
            alpha = 0.5f
        }
    }
}
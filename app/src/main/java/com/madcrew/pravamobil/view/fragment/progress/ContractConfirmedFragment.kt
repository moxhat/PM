package com.madcrew.pravamobil.view.fragment.progress

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentContractConfirmedBinding
import com.madcrew.pravamobil.databinding.FragmentPaymentSummBinding
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.utils.previousFragmentInProgress
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.utils.setVisible
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.checkdata.CheckDataFragment
import com.madcrew.pravamobil.view.fragment.progress.training.TrainingFragment


class ContractConfirmedFragment(var type: String = "good") : Fragment() {

    private var _binding: FragmentContractConfirmedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContractConfirmedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (type == "good"){
            binding.contractConfirmedImage.setImageResource(R.drawable.ic_woman_good)
            binding.contractConfirmedTitle.setText(R.string.contract_confirmed)
            binding.contractBadAnnotation.setGone()
            binding.btContractBadOk.setGone()
            (this.context as ProgressActivity).updateProgress("ContractComplete")
            Handler(Looper.getMainLooper()).postDelayed({
                nextFragmentInProgress(parentFragmentManager, TrainingFragment())
            }, 3000)
        } else {
            binding.contractConfirmedImage.setImageResource(R.drawable.ic_woman_bad)
            binding.contractConfirmedTitle.setText(R.string.contract_nullable)
            binding.contractBadAnnotation.setVisible()
            binding.btContractBadOk.setVisible()
        }

        binding.btContractBadOk.setOnClickListener {
            previousFragmentInProgress(parentFragmentManager, CheckDataFragment())
        }


    }
}
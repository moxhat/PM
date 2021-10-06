package com.madcrew.pravamobil.view.fragment.education.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentPaymentsBinding
import com.madcrew.pravamobil.utils.alphaDown
import com.madcrew.pravamobil.utils.alphaUp
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.utils.setVisible
import com.madcrew.pravamobil.view.activity.education.EducationActivity
import com.madcrew.pravamobil.view.fragment.education.paymentshistory.PaymentsHistoryFragment


class PaymentsFragment : Fragment() {

    var _binding: FragmentPaymentsBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        hideMenu(binding.paymentsMenuConstraint)

        binding.paymentsIndebtednessCard.setGone()

        binding.btPaymentsPay.setOnClickListener {
            showMenu(binding.paymentsMenuConstraint)
        }
        binding.paymentsMenuConstraint.setOnClickListener {
            hideMenu(it)
        }

        binding.paymentsContractPriceContentConstraint.setOnClickListener{
            setIndebtedness()
        }


        binding.paymentsAdditionalServicesConstraint.setOnClickListener{
            showHistory()
        }

        binding.paymentsPayedContentConstraint.setOnClickListener{
            showHistory()
        }

        //Временно
        binding.paymentsIndebtednessConstraint.setOnClickListener {
            setUnIndebtedness()
        }
    }

    private fun showMenu(view: View){
        view.setVisible()
        view.alphaUp(100)
    }

    private fun hideMenu(view: View){
        view.setGone()
        view.alphaDown(100)
    }

    private fun setIndebtedness(){
        val parent = this.context as EducationActivity
        parent.setStatusBarColorRed()
        binding.paymentsTitleCard.setCardBackgroundColor(ContextCompat.getColorStateList(requireContext(), R.color.red_alert))
        binding.paymentsIndebtednessCard.setVisible()
        binding.btPaymentsPay.setBackgroundResource(R.drawable.ic_main_button_red)
    }

    private fun setUnIndebtedness(){
        val parent = this.context as EducationActivity
        parent.setStatusBarColorBlue()
        binding.paymentsTitleCard.setCardBackgroundColor(ContextCompat.getColorStateList(requireContext(), R.color.main))
        binding.paymentsIndebtednessCard.setGone()
        binding.btPaymentsPay.setBackgroundResource(R.drawable.ic_main_button)
    }

    private fun showHistory(){
        val mainManager = parentFragmentManager
        val transaction: FragmentTransaction = mainManager.beginTransaction()
        transaction.add(R.id.education_fragment_container, PaymentsHistoryFragment())
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        transaction.commit()
    }
}
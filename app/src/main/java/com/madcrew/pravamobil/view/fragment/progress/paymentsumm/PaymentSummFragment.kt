package com.madcrew.pravamobil.view.fragment.progress.paymentsumm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentPaymentSummBinding
import com.madcrew.pravamobil.databinding.FragmentTarifBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.requestmodels.ChekPaymentStatusRequest
import com.madcrew.pravamobil.models.requestmodels.CreatePaymentRequest
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.ContractConfirmedFragment
import com.madcrew.pravamobil.view.fragment.progress.PaymentWebViewFragment
import com.madcrew.pravamobil.view.fragment.progress.paymnetoptions.PaymentOptionsFragment


class PaymentSummFragment(var paymentId: String = "empty") : Fragment() {

    private var _binding: FragmentPaymentSummBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentSummBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parent = this.context as ProgressActivity
             getPaymentStatus(parent)

        parent.updateProgress("ConfirmContractPage")

        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()

        val swipeContainer = binding.swipeLayout

        parent.mViewModel.createPayment.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                when (response.body()!!.status) {
                    "done" -> {
                        Preferences.setPrefsString("canceled", "false", requireContext())
                        val transaction: FragmentTransaction =
                            parentFragmentManager.beginTransaction()
                        transaction.apply {
                            setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out)
                            add(
                                R.id.progress_activity_fragment_container,
                                PaymentWebViewFragment(paymentUrl = response.body()!!.url.toString())
                            )
                            commit()
                            setButtonPayInstance(0)
                        }
                    }
                    "fail" -> showServerError(requireContext())
                }
            } else {
                showServerError(requireContext())
            }
        })

        parent.mViewModel.paymentStatus.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                when (response.body()!!.status){
                     "done" -> {
                        swipeContainer.isRefreshing = false
                        swipeContainer.isEnabled = true
                        binding.paymentSummSumm.text = "${response.body()!!.amount} руб."
                        when (response.body()!!.payStatus) {
                            "empty" -> {
                                setButtonPayInstance(1)
                            }
                            "pending" -> {
                                setButtonPayInstance(1)
            //                            Toast.makeText(requireContext(), resources.getString(R.string.pending), Toast.LENGTH_SHORT).show()
                            }
                            "waiting_for_capture" -> {
                                Toast.makeText(requireContext(), resources.getString(R.string.pending), Toast.LENGTH_SHORT).show()
                            }
                            "succeeded" -> {
                                nextFragmentInProgress(parentFragmentManager, ContractConfirmedFragment("good"))
                            }
                            "canceled" -> {
                                nextFragmentInProgress(parentFragmentManager, ContractConfirmedFragment("bad"))
                                parent.mViewModel.paymentStatus.removeObservers(viewLifecycleOwner)
                                Preferences.setPrefsString("canceled", "true", requireContext())
                            }
                        }
                    }
                    "fail" -> {
                        Toast.makeText(requireContext(), resources.getString(R.string.no_payment), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        showServerError(requireContext())
                    }
                }
            } else {
                showServerError(requireContext())
            }
        })

        swipeContainer.setOnRefreshListener {
            swipeContainer.isEnabled = false
            getPaymentStatus(parent)
        }

        binding.btPaymentSummPay.setOnClickListener {
            if (isOnline(requireContext())){
                parent.mViewModel.createNewPayment(
                    CreatePaymentRequest(
                        TOKEN,
                        schoolId.toInt(),
                        clientId.toInt(),
                        true
                    )
                )
            } else {
                noInternet(requireContext())
            }
        }
    }

    fun setButtonPayInstance(instance: Int) {
        if (instance == 0) {
            binding.forChekArrow.setVisible()
            binding.paymentSummForCheck.setVisible()
            binding.btPaymentSummPay.setDisable()
        } else {
            binding.forChekArrow.setGone()
            binding.paymentSummForCheck.setGone()
            binding.btPaymentSummPay.setEnable()
        }
    }

    private fun getPaymentStatus(parent: ProgressActivity){
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        if (isOnline(requireContext())){
            parent.mViewModel.chekPaymentStatus(ChekPaymentStatusRequest(TOKEN, schoolId.toInt(), clientId.toInt(), false, true))
        } else {
            noInternet(requireContext())
        }
    }
}
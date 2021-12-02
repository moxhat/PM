package com.madcrew.pravamobil.view.fragment.education.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentPaymentsBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.CreatePaymentRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.education.EducationActivity
import com.madcrew.pravamobil.view.fragment.education.payments.paymentshistory.PaymentsHistoryFragment


class PaymentsFragment : Fragment() {

    var _binding: FragmentPaymentsBinding? = null
    val binding get() = _binding!!
    lateinit var mViewModel: PaymentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parent = this.context as EducationActivity

        var _type = ""
        var _url = ""

        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        val repository = Repository()
        val viewModelFactory = PaymentsViewModelFactory(repository)

        val contractPrice = binding.paymentsContractPrice
        val nearestPaymentTitle = binding.paymentsTitle
        val nearestPaymentTitleSum = binding.paymentTitleSum
        val payedPayment = binding.paymentsPayed
        val anotherSum = binding.paymentsPayedTitle2
        val additionalPayments = binding.paymentsAdditionalServices
        val contractPayed = binding.paymentContractPayed

        mViewModel = ViewModelProvider(this, viewModelFactory).get(PaymentsViewModel::class.java)

        mViewModel.getPayInfo(SpravkaStatusRequest(TOKEN, schoolId, clientId))

        mViewModel.payInfo.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful){
                if (response.body()!!.status == "done"){
                    if (response.body()!!.nextAmount != 0){
                        nearestPaymentTitle.setVisible()
                        nearestPaymentTitleSum.setVisible()
                        contractPrice.text = "${response.body()!!.amount} рублей"
                        nearestPaymentTitle.text = "${resources.getString(R.string.nearest_payment_by)} ${response.body()!!.nextDate}"
                        nearestPaymentTitleSum.text = "${response.body()!!.nextAmount} рублей"
                        payedPayment.text = "${response.body()!!.pay} рублей"
                        anotherSum.text = "${resources.getString(R.string.not_payed)} ${response.body()!!.debt} рублей"
                        additionalPayments.text = "${response.body()!!.sPay} рублей"
                        contractPayed.setGone()
                        when (response.body()!!.expired) {
                            true -> {
                                setIndebtedness()
                            }
                            else -> {
                                setUnIndebtedness()
                            }
                        }
                    } else {
                        contractPayed.setVisible()
                        nearestPaymentTitle.setGone()
                        nearestPaymentTitleSum.setGone()
                        contractPrice.text = "${response.body()!!.amount} рублей"
                        anotherSum.text = resources.getString(R.string.no_indebtedness)
                        payedPayment.text = "${response.body()!!.pay} рублей"
                    }
                } else {
                    showServerError(requireContext())
                }
            } else {
                showServerError(requireContext())
            }
        })

        parent.mViewModel.createPayment.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                when (response.body()!!.status) {
                    "done" -> {
                        _url = response.body()!!.url.toString()
                        parent.starPaymentsOption( type = _type)
                    }
                    else -> showServerError(requireContext())
                }
            } else showServerError(requireContext())
        })

        hideMenu(binding.paymentsMenuConstraint)

        binding.paymentsIndebtednessCard.setGone()

        binding.btPaymentsPay.setOnClickListener {
            showMenu(binding.paymentsMenuConstraint)
        }
        binding.paymentsMenuConstraint.setOnClickListener {
            hideMenu(it)
        }

        binding.paymentsAdditionalServicesConstraint.setOnClickListener{
        }

        binding.paymentsPayedContentConstraint.setOnClickListener{
            _type = "history"
            parent.starPaymentsOption(type = _type)
        }

        binding.btPaymentsMenuNearestPayment.setOnClickListener {
            _type = "paymentNearest"
            parent.mViewModel.createNewPayment(CreatePaymentRequest(TOKEN, schoolId.toInt(), clientId.toInt(),false, true ))
        }

        binding.btPaymentsMenuPayAll.setOnClickListener {
            _type = "paymentAll"
            parent.mViewModel.createNewPayment(CreatePaymentRequest(TOKEN, schoolId.toInt(), clientId.toInt(),false, false ))
        }

        binding.btPaymentsMenuPayAdditional.setOnClickListener {
//            parent.starPaymentsOption("additional")
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

    fun nextFragmentInProgress(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.apply {
            setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out)
            replace(R.id.progress_activity_fragment_container, fragment)
            commit()
        }
    }
}
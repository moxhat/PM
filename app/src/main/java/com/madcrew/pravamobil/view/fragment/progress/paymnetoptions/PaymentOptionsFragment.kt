package com.madcrew.pravamobil.view.fragment.progress.paymnetoptions

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentPymentOptionsBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.models.submodels.PaymentModel
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.documenttype.DocumentTypeFragment
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem


class PaymentOptionsFragment() : Fragment() {

    private var _binding: FragmentPymentOptionsBinding? = null
    private val binding get() = _binding!!
    private var summ = 0
    private var firstPayment = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPymentOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerPayment = binding.paymentOptionsSpinner
        val paymentSeekCard = binding.cardPaymentSeek
        val paymentDatesCard = binding.cardPaymentDates

        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()

        val parent = this.context as ProgressActivity

        val mainManager = parentFragmentManager

        parent.updateProgress("SelectPayment")
        parent.mViewModel.getTariffInfo(SpravkaStatusRequest(TOKEN, schoolId, clientId))

        paymentSeekCard.setGone()
        paymentDatesCard.setGone()

        val seekBar = binding.paymentsOptionsSeekBar

        binding.btPaymentOptionsNext.setDisable()

        parent.mViewModel.tariffInfo.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                if (response.body()!!.status == "done") {
                    summ = response.body()!!.amount!!.toInt()
                    var spinnerItemArray = ArrayList<IconSpinnerItem>()
                    var startIndex = false
                    if (response.body()!!.credit == "true" && response.body()!!.fullPay == "true") {
                        spinnerItemArray = arrayListOf(
                            IconSpinnerItem(text = "Полная оплата", iconRes = null),
                            IconSpinnerItem(text = "Рассрочка 1 месяц", iconRes = null)
                        )
                    } else {
                        startIndex = true
                        if (response.body()!!.credit == "false"){
                            spinnerItemArray = arrayListOf(
                                IconSpinnerItem(text = "Полная оплата", iconRes = null),
                            )
                        }
                        if (response.body()!!.fullPay == "false") {
                            spinnerItemArray = arrayListOf(
                                IconSpinnerItem(text = "Рассрочка 2 месяца", iconRes = null)
                            )
                        }
                    }
                    spinnerPayment.apply {
                        setSpinnerAdapter(IconSpinnerAdapter(this))
                        setItems(spinnerItemArray)
                        getSpinnerRecyclerView().layoutManager = GridLayoutManager(context, 1)
                        if (startIndex) {
                            selectItemByIndex(0)
                        }
                        lifecycleOwner = this@PaymentOptionsFragment
                    }
                    spinnerPayment.setOnSpinnerItemSelectedListener<Any> { oldIndex, oldItem, newIndex, newText ->
                        if (newIndex == 0) {
                            paymentSeekCard.setGone()
                            paymentDatesCard.setGone()
                            binding.btPaymentOptionsNext.setEnable()
                        } else {
                            binding.btPaymentOptionsNext.setEnable()
                            paymentSeekCard.setVisible()
                            paymentDatesCard.setVisible()
                            val firstPayment = seekBar.progress * 1000
                            val nextPayment = summ - firstPayment
                            val progressString = "${(seekBar.progress * 1000)} руб."
                            binding.paymentValue.text = progressString
                            binding.paymentSum1.text = "$firstPayment руб."
                            binding.paymentSum2.text = "$nextPayment руб."
                        }
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        seekBar.min = 5
                    }
                    seekBar.max = summ / 1000 - 1
                    seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onStopTrackingTouch(seekBar: SeekBar) {}
                        override fun onStartTrackingTouch(seekBar: SeekBar) {}
                        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                            if (fromUser) {
                                if (progress >= 0 && progress <= seekBar.max) {
                                    if (progress <= 5){
                                        firstPayment = 5000
                                        val nextPayment = summ - firstPayment
                                        val progressString = "${(5000)} руб."
                                        binding.paymentValue.text = progressString
                                        binding.paymentSum1.text = "$firstPayment руб."
                                        binding.paymentSum2.text = "$nextPayment руб."
                                        seekBar.secondaryProgress = 5
                                    } else {
                                        firstPayment = progress * 1000
                                        val nextPayment = summ - firstPayment
                                        val progressString = "${(progress * 1000)} руб."
                                        binding.paymentValue.text = progressString
                                        binding.paymentSum1.text = "$firstPayment руб."
                                        binding.paymentSum2.text = "$nextPayment руб."
                                        seekBar.secondaryProgress = progress
                                    }
                                }
                            }
                        }
                    })
                    seekBar.setProgress(5, false)
                    binding.paymentValue.text = (seekBar.progress * 1000).toString()
                } else {
                    showServerError(requireContext())
                }
            } else {
                showServerError(requireContext())
            }
        })

        spinnerPayment.setOnSpinnerDismissListener {
            spinnerPayment.setBackgroundResource(R.drawable.ic_rectangle_light_gray)
        }

        spinnerPayment.setOnClickListener {
            spinnerPayment.setBackgroundResource(R.drawable.ic_rectangle_light_gray_half_radius)
            spinnerPayment.showOrDismiss()
        }

        binding.btPaymentOptionsNext.setOnClickListener {
            if (spinnerPayment.selectedIndex == 0){
                parent.updateClientData(FullRegistrationRequest(TOKEN, clientId, schoolId, pay = PaymentModel(spinnerPayment.selectedIndex.toString(), parent.mViewModel.tariffInfo.value!!.body()!!.amount, parent.mViewModel.tariffInfo.value!!.body()!!.amount)))
            } else {
                parent.updateClientData(FullRegistrationRequest(TOKEN, clientId, schoolId, pay = PaymentModel(spinnerPayment.selectedIndex.toString(), firstPayment.toString(), parent.mViewModel.tariffInfo.value!!.body()!!.amount)))
            }
            nextFragmentInProgress(mainManager, DocumentTypeFragment())
        }
    }
}
package com.madcrew.pravamobil.view.fragment.education.payments.additionalpayments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.AdditionalPaymentsFragmentBinding
import com.madcrew.pravamobil.databinding.FragmentPaymentsBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.requestmodels.AdditionalPaymentRequest
import com.madcrew.pravamobil.models.requestmodels.CategoryRequest
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.fragment.education.payments.PaymentsViewModelFactory
import com.madcrew.pravamobil.view.fragment.progress.PaymentWebViewFragment
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem

class AdditionalPaymentsFragment : Fragment() {

    var _binding: AdditionalPaymentsFragmentBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: AdditionalPaymentsViewModel
    private var spinnerItemArray = mutableListOf<IconSpinnerItem>()
    private var _selectedIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AdditionalPaymentsFragmentBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()

        val repository = Repository()
        val viewModelFactory = AdditionalPaymentsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AdditionalPaymentsViewModel::class.java)

        val spinnerPayment = binding.additionalPaymentsSpinner

        binding.btAdditionalPaymentsPay.setDisable()

        if (isOnline(requireContext())){
            viewModel.getAdditionalServices(CategoryRequest(TOKEN, schoolId))
        } else {
            noInternet(requireContext())
        }

        viewModel.additionalServices.observe(viewLifecycleOwner){ response ->
            if (response.isSuccessful){
                if (response.body()!!.status == "done"){
                    spinnerItemArray.clear()
                    for (i in response.body()!!.prices!!){
                        spinnerItemArray.add(IconSpinnerItem(i.title.toString(), null))
                    }
                    spinnerPayment.apply {
                        setSpinnerAdapter(IconSpinnerAdapter(this))
                        setItems(spinnerItemArray)
                        getSpinnerRecyclerView().layoutManager = GridLayoutManager(context, 1)
                        if (response.body()!!.prices!!.size == 1) {
                            selectItemByIndex(0)
                        }
                    }
                    spinnerPayment.setOnSpinnerDismissListener {
                        spinnerPayment.setBackgroundResource(R.drawable.ic_rectangle_light_gray)
                    }

                    spinnerPayment.setOnClickListener {
                        spinnerPayment.setBackgroundResource(R.drawable.ic_rectangle_light_gray_half_radius)
                        spinnerPayment.showOrDismiss()
                    }

                    spinnerPayment.setOnSpinnerItemSelectedListener<Any> { oldIndex, oldItem, newIndex, newText ->
                        _selectedIndex = newIndex
                        binding.additionalPaymentsPrice.text = "${viewModel.additionalServices.value!!.body()!!.prices!![newIndex].price} руб."
                        binding.btAdditionalPaymentsPay.setEnable()
                    }
                } else {
                    showServerError(requireContext())
                }
            } else {
                showServerError(requireContext())
            }
        }

        viewModel.additionalPayment.observe(viewLifecycleOwner){response ->
            if (response.isSuccessful){
                if (response.body()!!.status == "done"){
                    parentFragmentManager.beginTransaction().apply {
                        setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        add(R.id.payments_option_fragment_container, PaymentWebViewFragment(first = false, paymentUrl = response.body()!!.url.toString()))
                        commit()
                    }
                } else {
                    showServerError(requireContext())
                }
            } else {
                showServerError(requireContext())
            }
        }


        binding.additionalPaymentsAmountText.doOnTextChanged{_, _, _, _ ->
            if (binding.additionalPaymentsAmountText.text!!.length != 0){
                binding.additionalPaymentsSummSumm.text = "${binding.additionalPaymentsAmountText.text.toString().toInt() * viewModel.additionalServices.value!!.body()!!.prices!![_selectedIndex]!!.price!!} рублей"
            }
        }

        binding.additionalPaymentsMainConstrant.setOnClickListener {
            binding.additionalPaymentsAmountText.clearFocus()
        }

        binding.btAdditionalPaymentsPay.setOnClickListener {
            viewModel.createAdditionalPayment(AdditionalPaymentRequest(TOKEN, schoolId.toInt(), clientId.toInt(), viewModel.additionalServices.value!!.body()!!.prices!![_selectedIndex].id!!.toInt(), binding.additionalPaymentsAmountText.text.toString().toInt()))
        }

        binding.btAdditionalPaymentsBack.setOnClickListener {
            this.activity?.finish()
        }
    }

}
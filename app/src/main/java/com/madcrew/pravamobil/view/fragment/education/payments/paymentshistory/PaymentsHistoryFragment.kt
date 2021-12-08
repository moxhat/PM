package com.madcrew.pravamobil.view.fragment.education.payments.paymentshistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.LessonHistoryRecyclerAdapter
import com.madcrew.pravamobil.databinding.FragmentPaymentsHistoryBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.models.LessonsData
import com.madcrew.pravamobil.models.requestmodels.SpravkaStatusRequest
import com.madcrew.pravamobil.utils.Preferences
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.utils.showServerError
import com.madcrew.pravamobil.view.activity.paymentsoptions.PaymentsOptionsActivity
import com.madcrew.pravamobil.view.activity.paymentsoptions.PaymentsOptionsViewModel


class PaymentsHistoryFragment(var type: String = "history") : Fragment(), LessonHistoryRecyclerAdapter.OnStatusClickListener {

    var _binding: FragmentPaymentsHistoryBinding? = null
    val binding get() = _binding!!

    private lateinit var mAdapter: LessonHistoryRecyclerAdapter
    private var mPaymentsList = mutableListOf<LessonsData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentsHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parent = this.context as PaymentsOptionsActivity
        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()

        when (type) {
            "history" -> parent.mViewModel.getPaymentHistory(SpravkaStatusRequest(TOKEN, schoolId, clientId))
        }

        binding.paymentsHistoryTitle.setGone()

        parent.mViewModel.paymentHistory.observe(viewLifecycleOwner){ response ->
            if (response.isSuccessful) {
                if (response.body()!!.status == "done"){
                    mPaymentsList.clear()
                    for (i in response.body()!!.history!!){
                        mPaymentsList.add(LessonsData(i.date, "${i.price} руб.", i.method, 1.0))
                    }
                    mAdapter.notifyDataSetChanged()
                    binding.paymentHistoryTitleSum.text = response.body()!!.sum.toString() + " руб."
                } else {
                    showServerError(requireContext())
                }
            } else {
                showServerError(requireContext())
            }
        }

        mAdapter = LessonHistoryRecyclerAdapter(mPaymentsList, this)

        binding.paymentsHistoryRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }

        binding.btPaymentsHistoryBack.setOnClickListener {
            parent.finish()
        }

    }

    override fun onStatusClick(itemView: View?, position: Int) {

    }


}
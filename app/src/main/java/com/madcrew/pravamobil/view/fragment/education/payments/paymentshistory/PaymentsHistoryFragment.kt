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
import com.madcrew.pravamobil.models.LessonsData
import com.madcrew.pravamobil.view.activity.paymentsoptions.PaymentsOptionsActivity
import com.madcrew.pravamobil.view.activity.paymentsoptions.PaymentsOptionsViewModel


class PaymentsHistoryFragment : Fragment(), LessonHistoryRecyclerAdapter.OnStatusClickListener {

    var _binding: FragmentPaymentsHistoryBinding? = null
    val binding get() = _binding!!

    private lateinit var mAdapter: LessonHistoryRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        val mPaymentsList = mutableListOf(
            LessonsData("12.10.21 (вт)", "5 000 руб.", "Карта", 1.0),
            LessonsData("12.10.21 (вт)", "5 000 руб.", "Наличные", 1.0),
            LessonsData("12.10.21 (вт)", "5 000 руб.", "Наличные", 1.0),
            LessonsData("12.10.21 (вт)", "5 000 руб.", "Карта", 1.0),
            LessonsData("12.10.21 (вт)", "5 000 руб.", "Карта", 1.0),
            LessonsData("12.10.21 (вт)", "5 000 руб.", "Карта", 1.0),
            LessonsData("12.10.21 (вт)", "5 000 руб.", "Наличные", 1.0),
            LessonsData("12.10.21 (вт)", "5 000 руб.", "Наличные", 1.0),
            LessonsData("12.10.21 (вт)", "5 000 руб.", "Карта", 1.0),
            LessonsData("12.10.21 (вт)", "5 000 руб.", "Карта", 1.0)

            )

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
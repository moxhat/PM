package com.madcrew.pravamobil.view.fragment.progress.paymnetoptions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.GridLayoutManager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.databinding.FragmentPymentOptionsBinding
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.utils.setVisible
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem


class PaymentOptionsFragment : Fragment() {

    private var _binding: FragmentPymentOptionsBinding? = null
    private val binding get() = _binding!!

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

        paymentSeekCard.setGone()
        paymentDatesCard.setGone()

        spinnerPayment.apply {
            setSpinnerAdapter(IconSpinnerAdapter(this))
            setItems(
                arrayListOf(
                    IconSpinnerItem(text = "Полная оплата", iconRes = null),
                    IconSpinnerItem(text = "Рассрочка 2 месяца", iconRes = null)
                )
            )
            getSpinnerRecyclerView().layoutManager = GridLayoutManager(context, 1)
//            selectItemByIndex(0) // select an item initially.
            lifecycleOwner = this@PaymentOptionsFragment
        }

        spinnerPayment.setOnSpinnerDismissListener {
            spinnerPayment.setBackgroundResource(R.drawable.ic_rectangle_light_gray)
        }

        spinnerPayment.setOnClickListener {
            spinnerPayment.setBackgroundResource(R.drawable.ic_rectangle_light_gray_half_radius)
            spinnerPayment.showOrDismiss()
        }


        spinnerPayment.setOnSpinnerItemSelectedListener<Any> { oldIndex, oldItem, newIndex, newText ->
            if (newIndex == 0) {
                paymentSeekCard.setGone()
                paymentDatesCard.setGone()
            } else {
                paymentSeekCard.setVisible()
                paymentDatesCard.setVisible()
            }
        }

        val tempPrice = 30000

        val seekBar = binding.paymentsOptionsSeekBar
        seekBar.min = 5
        seekBar.max = tempPrice / 1000 - 1

        binding.paymentValue.text = (seekBar.progress * 1000).toString()
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    if (progress >= 0 && progress <= seekBar.max) {
                        val firstPayment = progress * 1000
                        val nextPayment = tempPrice - firstPayment
                        val progressString = "${(progress * 1000)} руб."
                        binding.paymentValue.text = progressString
                        binding.paymentSum1.text = "$firstPayment руб."
                        binding.paymentSum2.text = "$nextPayment руб."
                        seekBar.secondaryProgress = progress
                    }
                }
            }
        })
    }
}
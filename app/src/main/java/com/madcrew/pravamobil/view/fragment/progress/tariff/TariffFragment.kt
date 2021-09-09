package com.madcrew.pravamobil.view.fragment.progress.tariff

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.TariffSliderAdapter
import com.madcrew.pravamobil.databinding.FragmentTarifBinding
import com.madcrew.pravamobil.models.TariffSliderData
import com.madcrew.pravamobil.utils.nextFragmentInProgress
import com.madcrew.pravamobil.utils.previousFragmentInProgress
import com.madcrew.pravamobil.view.fragment.progress.paymnetoptions.PaymentOptionsFragment
import com.madcrew.pravamobil.view.fragment.progress.theorygroup.TheoryGroupFragment
import kotlin.math.abs

lateinit var sliderAdapter: TariffSliderAdapter
lateinit var tariffSlides:MutableList<TariffSliderData>

class TariffFragment : Fragment(), TariffSliderAdapter.OnSelectClickListener {

    private var _binding: FragmentTarifBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTarifBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tariffSlides = mutableListOf(
            TariffSliderData("Стандарт", "23 000 руб."),
            TariffSliderData("Продвинутый", "28 500 руб."),
            TariffSliderData("VIP", "32 000 руб.")
        )

        sliderAdapter = TariffSliderAdapter(tariffSlides, this)

        val viewPager = binding.tariffViewPager

        viewPager.apply {
            adapter = sliderAdapter
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit
        }
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(20))
        compositePageTransformer.addTransformer { page, position ->
            val r: Float = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        viewPager.setPageTransformer(compositePageTransformer)
        sliderAdapter.notifyDataSetChanged()
        (viewPager.adapter as TariffSliderAdapter).notifyDataSetChanged()
//        setupIndicators()

        val indicatorsContainer = binding.tariffIndicators
        val indicators = arrayOfNulls<ImageView>(sliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(10, 0, 10, 0)
        sliderAdapter.notifyDataSetChanged()
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicatorsContainer.addView(indicators[i])
        }
        sliderAdapter.notifyDataSetChanged()

        setupCurrentIndicator(0)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setupCurrentIndicator(position)
            }
        })

        binding.btTariffNext.setOnClickListener {
            viewPager.currentItem += 1
        }

        binding.btTariffPrevious.setOnClickListener {
            viewPager.currentItem -= 1
        }

        binding.btTariffBack.setOnClickListener {
            val mainManager = parentFragmentManager
            previousFragmentInProgress(mainManager, TheoryGroupFragment())
        }

    }

    override fun onSelectClick(itemView: View?, position: Int) {
        val mainManager = parentFragmentManager
        nextFragmentInProgress(mainManager, PaymentOptionsFragment())
    }

    private fun setupCurrentIndicator(index: Int) {
        val indicatorsContainer = binding.tariffIndicators
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_indicator_inactive
                    )
                )
            }
        }
    }
}
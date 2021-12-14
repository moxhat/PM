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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.TariffSliderAdapter
import com.madcrew.pravamobil.databinding.FragmentTarifBinding
import com.madcrew.pravamobil.domain.BaseUrl.Companion.TOKEN
import com.madcrew.pravamobil.domain.Repository
import com.madcrew.pravamobil.models.TariffSliderData
import com.madcrew.pravamobil.models.requestmodels.FullRegistrationRequest
import com.madcrew.pravamobil.models.requestmodels.TariffListRequest
import com.madcrew.pravamobil.models.responsemodels.Tariff
import com.madcrew.pravamobil.utils.*
import com.madcrew.pravamobil.view.activity.progress.ProgressActivity
import com.madcrew.pravamobil.view.fragment.progress.category.CategoryViewModel
import com.madcrew.pravamobil.view.fragment.progress.category.CategoryViewModelFactory
import com.madcrew.pravamobil.view.fragment.progress.paymnetoptions.PaymentOptionsFragment
import com.madcrew.pravamobil.view.fragment.progress.theorygroup.TheoryGroupFragment
import kotlin.math.abs

private lateinit var sliderAdapter: TariffSliderAdapter
private var tariffSlides = mutableListOf<Tariff>()
private var selectedPage = 0

class TariffFragment : Fragment(), TariffSliderAdapter.OnSelectClickListener {

    private var _binding: FragmentTarifBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()

        val parent = this.context as ProgressActivity
        val repository = Repository()
        val viewModelFactory = TariffViewModelFactory(repository)
        val mViewModel =
            ViewModelProvider(this, viewModelFactory).get(TariffViewModel::class.java)

        parent.updateProgress("SelectTariffPage")

        if (isOnline(requireContext())){
            mViewModel.getTariffList(TariffListRequest(TOKEN, schoolId, clientId))
        }

        val viewPager = binding.tariffViewPager
        sliderAdapter = TariffSliderAdapter(tariffSlides, this)

        mViewModel.tariffResponse.observe(viewLifecycleOwner, {respone ->
            if (respone.isSuccessful){
                if(respone.body()!!.status == "done"){
                    tariffSlides.clear()
                    for (i in respone.body()!!.tariffs){
                        tariffSlides.add(i)
                    }
                    println(tariffSlides)
                    sliderAdapter.notifyDataSetChanged()
                    viewPager.apply {
                        adapter = sliderAdapter
                        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                        clipToPadding = false
                        clipChildren = false
                        offscreenPageLimit = 3
                    }

                    if (respone.body()!!.tariffs.size == 1){
                        hideNavButtons(true)
                    } else {
                        hideNavButtons(false)
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
                }
            }
        })


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setupCurrentIndicator(position)
                selectedPage = position
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
            val filialId = Preferences.getPrefsString("filialId", requireContext()).toString()
            previousFragmentInProgress(mainManager, TheoryGroupFragment(filialId))
        }

        binding.btTariffChangeSchool.setOnClickListener {
            parent.changeSchool()
        }

    }

    override fun onSelectClick(itemView: View?, position: Int) {
        val mainManager = parentFragmentManager
        val parent = this.context as ProgressActivity
        val schoolId = Preferences.getPrefsString("schoolId", requireContext()).toString()
        val clientId = Preferences.getPrefsString("clientId", requireContext()).toString()
        parent.updateClientData(FullRegistrationRequest(TOKEN, clientId, schoolId, tariffId = tariffSlides[position].id))
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

    private fun hideNavButtons(need: Boolean){
        if (need) {
            binding.btTariffNext.setGone()
            binding.btTariffPrevious.setGone()
        } else {
            binding.btTariffNext.setVisible()
            binding.btTariffPrevious.setVisible()
        }
    }
}
package com.madcrew.pravamobil.view.fragment.progress.training

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.adapter.TrainingSliderAdapter
import com.madcrew.pravamobil.databinding.FragmentTrainingBinding
import com.madcrew.pravamobil.models.TrainingSliderData

lateinit var trainingSliderAdapter: TrainingSliderAdapter
lateinit var trainingSlides:MutableList<TrainingSliderData>

class TrainingFragment : Fragment(), TrainingSliderAdapter.OnOkClickListener {

    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trainingSlides = mutableListOf(
            TrainingSliderData(R.drawable.ic_woman_with_book, "Обучение начинается с теории", "После запуска группы приложение подскажет дальнейшие действия", null),
            TrainingSliderData(R.drawable.ic_woman_with_clock,"Шкала прогресса", "показывает ваше приближение к получению прав.", R.drawable.ic_progress_training),
            TrainingSliderData(R.drawable.ic_woman_with_tablet, "Успешного обучения!", null, null)
        )

        trainingSliderAdapter = TrainingSliderAdapter(trainingSlides, this)

        val viewPager = binding.trainingViewPager

        viewPager.adapter = trainingSliderAdapter
        viewPager.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        (viewPager.adapter as TrainingSliderAdapter).notifyDataSetChanged()

        val indicatorsContainer = binding.trainingIndicators
        val indicators = arrayOfNulls<ImageView>(trainingSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(10, 0, 10, 0)
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
        trainingSliderAdapter.notifyDataSetChanged()

        setupCurrentIndicator(0)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setupCurrentIndicator(position)
            }
        })
    }

    private fun setupCurrentIndicator(index: Int) {
        val indicatorsContainer = binding.trainingIndicators
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

    override fun onOkClick(itemView: View?, position: Int) {
        Toast.makeText(requireContext(), "Next!", Toast.LENGTH_SHORT).show()
    }


}
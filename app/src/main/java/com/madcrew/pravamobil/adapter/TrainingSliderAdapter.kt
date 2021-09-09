package com.madcrew.pravamobil.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.models.TariffSliderData
import com.madcrew.pravamobil.models.TrainingSliderData
import com.madcrew.pravamobil.utils.setGone
import com.madcrew.pravamobil.utils.setVisible


class TrainingSliderAdapter(private var slides: List<TrainingSliderData>, private val listenerTraining: OnOkClickListener)
    : RecyclerView.Adapter<TrainingSliderAdapter.SliderAdapterViewHolder>(){

    interface OnOkClickListener {
        fun onOkClick(itemView: View?, position: Int)
    }

    inner class SliderAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private val trainingTitleImage = view.findViewById<AppCompatImageView>(R.id.training_slider_image_title)
        private val trainingTitle = view.findViewById<AppCompatTextView>(R.id.training_slide_title_text)
        private val trainingProgress = view.findViewById<AppCompatImageView>(R.id.training_slide_progress_image)
        private val trainingText = view.findViewById<AppCompatTextView>(R.id.training_slide_text)
        val btTrainingOk = view.findViewById<AppCompatButton>(R.id.bt_training_slide_ok)!!


        fun bind(trainingSliderData: TrainingSliderData) {
            Glide.with(trainingTitleImage).load(trainingSliderData.trainingTitleImage).into(trainingTitleImage)
            trainingTitle.text = trainingSliderData.trainingTitleText
            Glide.with(trainingProgress).load(trainingSliderData.progressImage).into(trainingProgress)
            trainingText.text = trainingSliderData.trainingText
            btTrainingOk?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listenerTraining.onOkClick(v, absoluteAdapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderAdapterViewHolder {
        return SliderAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.trainin_slider_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderAdapterViewHolder, position: Int) {
        holder.bind(slides[position])
        if (position == slides.size - 1){
            holder.btTrainingOk.setVisible()
        } else {
            holder.btTrainingOk.setGone()
        }
    }

    override fun getItemCount(): Int {
        return slides.size
    }

}
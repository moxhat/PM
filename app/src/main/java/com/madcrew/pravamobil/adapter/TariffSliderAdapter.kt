package com.madcrew.pravamobil.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.models.TariffSliderData


class TariffSliderAdapter(private var slides: List<TariffSliderData>, private val listenerTariff: OnSelectClickListener)
    : RecyclerView.Adapter<TariffSliderAdapter.SliderAdapterViewHolder>(){

    interface OnSelectClickListener {
        fun onSelectClick(itemView: View?, position: Int)
    }

    inner class SliderAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private val tariffName = view.findViewById<TextView>(R.id.tariff_name)
        private val tariffPrice = view.findViewById<TextView>(R.id.tariff_card_price)
        private val btTariffSelect = view.findViewById<AppCompatButton>(R.id.bt_tariff_card_select)


        fun bind(tariffSliderData: TariffSliderData) {
            tariffName.text = tariffSliderData.tariffName
            tariffPrice.text = tariffSliderData.tariffPrice
            btTariffSelect.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listenerTariff.onSelectClick(v, absoluteAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderAdapterViewHolder {
        return SliderAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tariff_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderAdapterViewHolder, position: Int) {
        holder.bind(slides[position])
    }

    override fun getItemCount(): Int {
        return slides.size
    }

}
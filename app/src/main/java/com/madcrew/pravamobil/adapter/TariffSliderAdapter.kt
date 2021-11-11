package com.madcrew.pravamobil.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.models.responsemodels.Tariff


class TariffSliderAdapter(private var slides: List<Tariff>, private val listenerTariff: OnSelectClickListener)
    : RecyclerView.Adapter<TariffSliderAdapter.SliderAdapterViewHolder>(){

    interface OnSelectClickListener {
        fun onSelectClick(itemView: View?, position: Int)
    }

    inner class SliderAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private val tariffName = view.findViewById<TextView>(R.id.tariff_name)
        private val tariffPrice = view.findViewById<TextView>(R.id.tariff_card_price)
        private val tariffInclude0 = view.findViewById<TextView>(R.id.in_contract_text_0)
        private val tariffInclude1 = view.findViewById<TextView>(R.id.in_contract_text_1)
        private val tariffInclude2 = view.findViewById<TextView>(R.id.in_contract_text_2)
        private val tariffInclude3 = view.findViewById<TextView>(R.id.in_contract_text_3)
        private val tariffAdditional0 = view.findViewById<TextView>(R.id.tariff_additional_payment_0)
        private val tariffAdditional1 = view.findViewById<TextView>(R.id.tariff_additional_payment_1)
        private val btTariffSelect = view.findViewById<AppCompatButton>(R.id.bt_tariff_card_select)


        fun bind(tariff: Tariff) {
            tariffName.text = tariff.title
            tariffPrice.text = tariff.price + " " + "руб."
            when(tariff.include.size){
                1 ->  tariffInclude0.text = tariff.include[0]
                2 -> {
                    tariffInclude0.text = tariff.include[0]
                    tariffInclude1.text = tariff.include[1]
                }
                3 -> {
                    tariffInclude0.text = tariff.include[0]
                    tariffInclude1.text = tariff.include[1]
                    tariffInclude2.text = tariff.include[2]
                }
                4 -> {
                    tariffInclude0.text = tariff.include[0]
                    tariffInclude1.text = tariff.include[1]
                    tariffInclude2.text = tariff.include[2]
                    tariffInclude3.text = tariff.include[3]
                }
            }
            when(tariff.extra.size){
                1 -> tariffAdditional0.text = tariff.extra[0]
                2 -> {
                    tariffAdditional0.text = tariff.extra[0]
                    tariffAdditional1.text = tariff.extra[1]
                }
            }

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
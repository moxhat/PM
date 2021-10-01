package com.madcrew.pravamobil.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.models.InstructorDateData

class InstructorDatesRecyclerAdapter (
        private val list: MutableList<InstructorDateData>,
        private val listenerDate: OnDateClickListener
    ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        interface OnDateClickListener {
            fun onDateClick(v: Button?, position: Int)
        }

        private inner class ViewHolderDate internal constructor(itemView: View) :
            RecyclerView.ViewHolder(itemView),
            View.OnClickListener
        {

            internal var buttonDate: Button? = null

            init {
                buttonDate = itemView.findViewById(R.id.bt_instructor_time)
                buttonDate?.setOnClickListener(this)
            }

            internal fun bind(dates: InstructorDateData) {
                buttonDate?.text = dates.date
            }

            override fun onClick(v: View) {
                listenerDate.onDateClick(buttonDate, absoluteAdapterPosition)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ViewHolderDate(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.instructor_time_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val date = list[position] as InstructorDateData
            (holder as ViewHolderDate).bind(date)
        }

        override fun getItemCount(): Int {
            return list.size
        }
}
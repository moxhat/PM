package com.madcrew.pravamobil.adapter

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getColorStateList
import androidx.recyclerview.widget.RecyclerView
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.models.LessonsData

class LessonHistoryRecyclerAdapter(
    private var list: MutableList<LessonsData>,
    private val listenerStatus: OnStatusClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnStatusClickListener {
        fun onStatusClick(itemView: View?, position: Int)
    }

    fun setLessonsList(list: List<LessonsData>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    private inner class ViewHolderLessonHistory internal  constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var dateString: TextView? = null
        internal var nameString: TextView? = null
        internal var statusString: TextView? = null

        init {
            dateString = itemView.findViewById(R.id.vhh_first_element)
            nameString = itemView.findViewById(R.id.vhh_second_element)
            statusString = itemView.findViewById(R.id.vhh_third_element)
            itemView.setOnClickListener(this)
        }

        internal fun bind(lessons: LessonsData) {
            dateString?.text = lessons.date
            nameString?.text = lessons.name
            statusString?.text = lessons.status

            when (lessons.status){
                "Отмена" -> statusString?.setTextColor(ContextCompat.getColorStateList(statusString?.context!!, R.color.help_gray))
                "Неявка" -> statusString?.setTextColor(ContextCompat.getColorStateList(statusString?.context!!, R.color.red_alert))
                "Назначено" -> statusString?.setTextColor(ContextCompat.getColorStateList(statusString?.context!!, R.color.light_green))
                "Пройдено" -> statusString?.setTextColor(ContextCompat.getColorStateList(statusString?.context!!, R.color.gray_text))
                "Карта" -> statusString?.setTextColor(ContextCompat.getColorStateList(statusString?.context!!, R.color.light_green))
                "Наличные" -> statusString?.setTextColor(ContextCompat.getColorStateList(statusString?.context!!, R.color.main))
            }
        }

        override fun onClick(itemView: View?) {
            listenerStatus.onStatusClick(itemView, absoluteAdapterPosition)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderLessonHistory(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_history_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val lessons = list[position]
        (holder as LessonHistoryRecyclerAdapter.ViewHolderLessonHistory).bind(lessons)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
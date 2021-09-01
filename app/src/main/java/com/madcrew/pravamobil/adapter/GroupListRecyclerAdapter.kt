package com.madcrew.pravamobil.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.models.GroupTimes

class GroupListRecyclerAdapter(
    private var list: MutableList<GroupTimes>,
    private val listenerGroup: OnGroupClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var currentSelection = -1

    interface OnGroupClickListener {
        fun onGroupClick(itemView: View?, position: Int)
    }

    fun setGroupList(list: List<GroupTimes>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    private inner class ViewHolderGroupList internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var dateString: TextView? = null
        internal var dayTimeString: TextView? = null
        internal var groupCard: CardView? = null
        internal var dateTitle: TextView? = null
        internal var dayTimeTitle: TextView? = null
        internal var brakeLine: CardView? = null

        init {
            dateString = itemView.findViewById(R.id.view_holder_date)
            dayTimeString = itemView.findViewById(R.id.view_holder_time)
            groupCard = itemView.findViewById(R.id.view_holder_card)
            dateTitle = itemView.findViewById(R.id.view_holder_start_title)
            dayTimeTitle = itemView.findViewById(R.id.view_holder_time_title)
            brakeLine = itemView.findViewById(R.id.holder_break_line)
        }

        internal fun bind(group: GroupTimes) {
            dateString?.text = group.date
            dayTimeString?.text = group.dayTimes
            itemView.setOnClickListener(this)
        }

        override fun onClick(itemView: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                currentSelection = position
                listenerGroup.onGroupClick(itemView, adapterPosition)
                notifyDataSetChanged()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderGroupList(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_group, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val group = list[position]
        (holder as GroupListRecyclerAdapter.ViewHolderGroupList).bind(group)
        if (currentSelection == position){
            holder.groupCard?.setBackgroundResource(R.drawable.ic_main_button_up)
            holder.dateString?.setTextColor(Color.WHITE)
            holder.dayTimeString?.setTextColor(Color.WHITE)
            holder.dateTitle?.setTextColor(Color.WHITE)
            holder.dateTitle?.alpha = 0.5f
            holder.dayTimeTitle?.setTextColor(Color.WHITE)
            holder.dayTimeTitle?.alpha = 0.5f
            holder.brakeLine?.setBackgroundResource(R.drawable.ic_rectangle_white)
        } else {
            holder.groupCard?.setBackgroundResource(R.drawable.ic_rectangle_light_gray)
            holder.dateString?.setTextColor(Color.BLACK)
            holder.dayTimeString?.setTextColor(Color.BLACK)
            holder.dateTitle?.setTextColor(Color.BLACK)
            holder.dateTitle?.alpha = 0.4f
            holder.dayTimeTitle?.setTextColor(Color.BLACK)
            holder.dayTimeTitle?.alpha = 0.4f
            holder.brakeLine?.setBackgroundResource(R.drawable.ic_rectangle_light_gray_text)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

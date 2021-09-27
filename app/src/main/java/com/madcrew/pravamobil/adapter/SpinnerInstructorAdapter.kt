package com.madcrew.pravamobil.adapter

import android.content.Context
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.madcrew.pravamobil.R
import com.madcrew.pravamobil.models.InstructorSpinnerItem


class SpinnerInstructorAdapter(val context: Context, var items: MutableList<InstructorSpinnerItem>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.spinner_instructor_item, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = items[position].instructorName

        Glide.with(context).load(items[position].instructorFace).circleCrop().into(vh.img)

        return view
    }

    override fun getItem(position: Int): Any {
        return items[position];
    }

    override fun getCount(): Int {
        return items.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val label: TextView
        val img: ImageView

        init {
            label = row?.findViewById(R.id.spinner_item_title) as TextView
            img = row.findViewById(R.id.spinner_item_image) as ImageView
        }
    }

}


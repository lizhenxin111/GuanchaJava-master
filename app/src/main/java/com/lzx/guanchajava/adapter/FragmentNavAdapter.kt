package com.lzx.guanchajava.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.R

class FragmentNavAdapter : RecyclerView.Adapter<FragmentNavAdapter.Holder>() {

    var data = listOf<String>()
    lateinit var onClickListener : OnClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_fragment_nav, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.item.text = data[position]

        if (::onClickListener.isInitialized) {
            holder.itemView.setOnClickListener {
                onClickListener.onClick(position)
                holder.itemView.isSelected = true
            }
        }
    }


    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val item : TextView = view.findViewById(R.id.fragment_nav_item)
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }
}
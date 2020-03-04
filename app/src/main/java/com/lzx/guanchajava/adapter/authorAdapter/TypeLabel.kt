package com.lzx.guanchajava.adapter.authorAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.author.Item
import com.lzx.guanchajava.R

class TypeLabel : IAuthor {
    override fun isForViewType(item: Item): Boolean {
        return item.id == "LABEL"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_reply_label, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, item: Item) {
        val h = holder as Holder
        h.label.text = item.cate
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val label = view.findViewById<TextView>(R.id.reply_label)
    }
}
package com.lzx.guanchajava.adapter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.search.author.Item
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.BaseRecyclerViewAdapter
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.sdk27.coroutines.onClick

class SearchAuthorAdapter(val onClickListener: OnClickListener) : BaseRecyclerViewAdapter<Item>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_author, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as Holder
        val d = data[position]
        h.pic.apply {
            ifCircle = true
            url = d.image
        }
        h.name.text = d.name
        h.des.text = d.description
        h.itemView.onClick { onClickListener.onClick(d.id) }

    }
    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val pic = view.findViewById<UrlImageView>(R.id.author_pic)
        val name = view.findViewById<AppCompatTextView>(R.id.author_name)
        val des = view.findViewById<AppCompatTextView>(R.id.author_des)
    }

    interface OnClickListener {
        fun onClick(id: String)
    }
}
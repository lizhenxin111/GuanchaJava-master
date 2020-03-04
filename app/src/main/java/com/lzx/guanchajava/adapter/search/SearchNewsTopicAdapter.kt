package com.lzx.guanchajava.adapter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.search.newsTopic.Item
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.BaseRecyclerViewAdapter
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.sdk27.coroutines.onClick

class SearchNewsTopicAdapter(val onClickListener: OnClickListener) : BaseRecyclerViewAdapter<Item>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_news_topic, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as Holder
        val d = data[position]
        h.pic.url = d.image
        h.name.text = d.name
        h.itemView.onClick { onClickListener.onClick(d.id) }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val pic = view.findViewById<UrlImageView>(R.id.news_topic_pic)
        val name = view.findViewById<AppCompatTextView>(R.id.news_topic_name)
    }

    interface OnClickListener {
        fun onClick(id: String)
    }
}
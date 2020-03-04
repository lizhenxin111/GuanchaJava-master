package com.lzx.guanchajava.adapter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.search.news.Item
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.BaseRecyclerViewAdapter
import org.jetbrains.anko.sdk27.coroutines.onClick

class SearchNewsAdapter(val onClickListener: OnClickListener) : BaseRecyclerViewAdapter<Item>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_news, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as Holder
        val d = data[position]
        h.title.text = d.title
        h.time.text = d.created_at
        h.comment.text = d.comment_count.toString()
        h.itemView.onClick { onClickListener.onClick(d.id.toString()) }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<AppCompatTextView>(R.id.news_title)
        val time = view.findViewById<AppCompatTextView>(R.id.news_time)
        val comment = view.findViewById<AppCompatTextView>(R.id.news_comment)
    }

    interface OnClickListener {
        fun onClick(id: String)
    }
}
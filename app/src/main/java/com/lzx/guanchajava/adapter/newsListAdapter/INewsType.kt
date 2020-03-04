package com.lzx.guanchajava.adapter.newsListAdapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.newsList.News

interface INewsType {

    var onClickListener: OnClickListener

    fun isForViewType(news: News) : Boolean

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, news: News)

    interface OnClickListener {
        fun onTagClick(id: String)
        fun onClick(id: String, type: String)
        fun onLongClick(imageUrl: String, ancherView: View)
    }
}
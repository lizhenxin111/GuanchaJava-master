package com.lzx.guanchajava.adapter.authorAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.author.Item

interface IAuthor {
    fun isForViewType(item: Item) : Boolean
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, item: Item)
}
package com.lzx.guanchajava.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data = mutableListOf<T>()

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    override fun getItemCount() = data.size
    abstract override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)

}
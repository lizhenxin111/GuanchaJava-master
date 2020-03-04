package com.lzx.guanchajava.adapter.searchadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.R
import org.jetbrains.anko.sdk27.coroutines.onClick

class SearchHistoryAdapter(val onClickListener: OnSearchClickListener) : RecyclerView.Adapter<SearchHistoryAdapter.Holder>(){
    var data = listOf<String>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_hotkey, p0, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val d = data[p1]
        p0.word.text = d
        p0.word.onClick { onClickListener.onClick(d) }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val word = view.findViewById<TextView>(R.id.search_word)
    }
}
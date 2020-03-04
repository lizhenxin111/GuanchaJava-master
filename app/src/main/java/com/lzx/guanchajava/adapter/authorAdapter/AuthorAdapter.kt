package com.lzx.guanchajava.adapter.authorAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.author.Item
import org.jetbrains.anko.sdk27.coroutines.onClick

class AuthorAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var data: List<Item>
    lateinit var types: List<IAuthor>
    lateinit var onClickListener: OnClickListener
    override fun getItemViewType(position: Int): Int {
        val item = data[position]
        for (x in types) if (x.isForViewType(item)) return types.indexOf(x)
        throw Exception("AuthorAdapter: 未找到合适的适配器")
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return types[p1].onCreateViewHolder(p0, p1)
    }

    override fun getItemCount(): Int = if (::data.isInitialized) data.size else 0

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val type = types[p0.itemViewType]
        type.onBindViewHolder(p0, p1, data[p1])
        if (::onClickListener.isInitialized) {  p0.itemView.onClick { onClickListener.onClick(data[p1]) }        }
    }

    interface OnClickListener {        fun onClick(item: Item)    }
}
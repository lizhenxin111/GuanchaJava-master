package com.lzx.guanchajava.adapter.userDetailAdapter.userMessageDerail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.userDetail.messageDetail.Msg

class MessageListAdapter(val types: List<IMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data = mutableListOf<Msg>()

    override fun getItemViewType(position: Int): Int {
        for (x in types) return if (x.isForViewType(data[position])) 0 else 1
        throw Exception("MessageListAdapter: 未找到合适的类型")
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return types[p1].onCreateViewHolder(p0, p1)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val type = types[p0.itemViewType]
        type.onBindViewHolder(p0, p1, data[p1])
    }
}
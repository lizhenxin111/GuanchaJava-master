package com.lzx.guanchajava.adapter.replyAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.reply.BaseReply

class ReplyAdapter(val title: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data = mutableListOf<BaseReply>()
    var types = listOf<IReply>()

    override fun getItemViewType(position: Int): Int {
        val reply = data[position]
        for (x in types) {
            x.title = title
            if (x.isForViewType(reply)) return types.indexOf(x)
        }
        throw Exception("ReplyAdapter: 未找到合适的适配器")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return types[viewType].onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = types[holder.itemViewType]
        type.onBindViewHolder(holder, position, data[position])
    }


}
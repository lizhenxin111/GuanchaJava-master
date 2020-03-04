package com.lzx.guanchajava.adapter.replyAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.reply.BaseReply
import com.lzx.guanchajava.R

class TypeLabel(override var title: String) : IReply {
    override fun isForViewType(reply: BaseReply): Boolean {
        return reply.status == 20
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_reply_label, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, reply: BaseReply) {
        val h = holder as Holder
        h.label.text = reply.content
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val label = view.findViewById<TextView>(R.id.reply_label)
    }
}
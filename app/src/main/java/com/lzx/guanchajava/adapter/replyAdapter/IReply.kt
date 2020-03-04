package com.lzx.guanchajava.adapter.replyAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.reply.BaseReply

interface IReply {
    var title: String
    fun isForViewType(reply: BaseReply): Boolean
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, reply: BaseReply)
}
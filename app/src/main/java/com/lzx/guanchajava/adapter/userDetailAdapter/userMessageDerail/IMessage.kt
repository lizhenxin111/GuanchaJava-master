package com.lzx.guanchajava.adapter.userDetailAdapter.userMessageDerail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.userDetail.messageDetail.Msg

interface IMessage {

    fun isForViewType(msg: Msg) : Boolean

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, msg: Msg)

}
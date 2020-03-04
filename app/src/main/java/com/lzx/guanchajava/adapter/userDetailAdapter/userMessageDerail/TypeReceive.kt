package com.lzx.guanchajava.adapter.userDetailAdapter.userMessageDerail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.userDetail.messageDetail.Msg
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.UserDetailActivity
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class TypeReceive : IMessage {
    override fun isForViewType(msg: Msg): Boolean {
        return msg.isSender
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_message_receive, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, msg: Msg) {
        val h = holder as Holder
        h.time.text = msg.created_at
        h.pic.ifCircle = true
        h.pic.url = msg.avater
        h.content.text = msg.content
        h.pic.onClick {
            App.context.startActivity<UserDetailActivity>("UID" to msg.sender_id.toString())
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val time = view.findViewById<TextView>(R.id.message_time)
        val pic = view.findViewById<UrlImageView>(R.id.message_pic)
        val content = view.findViewById<TextView>(R.id.message_content)
    }
}
package com.lzx.guanchajava.adapter.userDetailAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.userDetail.message.Item
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.LoginUtil
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.sdk27.coroutines.onClick

class UserMessageAdapter(val onClickListener: OnClickListener) : RecyclerView.Adapter<UserMessageAdapter.Holder>() {

    var data = mutableListOf<Item>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_message, p0, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val d = data[p1]
        p0.pic.url = d.avatar
        p0.pic.ifCircle = true
        p0.time.text = d.created_at
        p0.name.text = d.user_nick
        p0.summary.text = d.content
        p0.itemView.onClick {
            val uid = if (LoginUtil.getUid() == d.receiver_id) d.sender_id.toString() else d.receiver_id.toString()
            onClickListener.onClick(uid, d.user_nick)
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val pic = view.findViewById<UrlImageView>(R.id.message_pic)
        val time = view.findViewById<TextView>(R.id.message_time)
        val name = view.findViewById<TextView>(R.id.message_name)
        val summary = view.findViewById<TextView>(R.id.message_summary)
    }

    interface OnClickListener {
        fun onClick(senderUid: String, senderName: String)
    }
}
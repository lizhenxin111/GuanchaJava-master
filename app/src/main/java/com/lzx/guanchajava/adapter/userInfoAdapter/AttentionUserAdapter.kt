package com.lzx.guanchajava.adapter.userInfoAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.userInfo.attentionUser.Data
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class AttentionUserAdapter : RecyclerView.Adapter<AttentionUserAdapter.Holder>() {
    var data = mutableListOf<Data>()
    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_attention_user, p0, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val d = data[p1]
        p0.pic.url = d.user_photo
        p0.name.text = d.user_nick
        if (d.has_attention) p0.paid.text = "已关注"
        p0.des.text = d.user_description

        if (::onClickListener.isInitialized) p0.itemView.onClick { onClickListener.onClick(d.id.toString()) }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val pic = view.find<UrlImageView>(R.id.atten_pic)
        val name = view.find<TextView>(R.id.atten_name)
        val paid = view.find<TextView>(R.id.atten_paid)
        val des = view.find<TextView>(R.id.atten_des)
    }

    interface OnClickListener {
        fun onClick(uid: String)
    }
}
package com.lzx.guanchajava.adapter.userInfoAdapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.userInfo.followed.Data
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class FollowedAdapter : RecyclerView.Adapter<FollowedAdapter.Holder>() {

    var data = mutableListOf<Data>()
    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_attention_user, p0, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val d = data[p1]
        p0.pic.url = d.user_photo
        p0.name.text = d.content
        if (!d.has_attention) p0.paid.text = "关注"
        p0.des.text = d.created_at

        if (!d.is_read) {
            p0.container.backgroundColor =
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) App.context.getColor(R.color.colorUnread)
                    else App.context.resources.getColor(R.color.colorUnread)
        }

        if (::onClickListener.isInitialized) p0.pic.onClick { onClickListener.onClick(d.code_id) }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val pic = view.find<UrlImageView>(R.id.atten_pic)
        val name = view.find<TextView>(R.id.atten_name)
        val paid = view.find<TextView>(R.id.atten_paid)
        val des = view.find<TextView>(R.id.atten_des)
        val container = view.find<ConstraintLayout>(R.id.atten_container)
    }

    interface OnClickListener {
        fun onClick(id: String)
    }
}
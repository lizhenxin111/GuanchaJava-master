package com.lzx.guanchajava.adapter.userDetailAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.userDetail.subscription.Data
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.ResourceUtil
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class UserSubscriptionAdapter  : RecyclerView.Adapter<UserSubscriptionAdapter.Holder>() {

    var data = mutableListOf<Data>()
    lateinit var onClickListener : OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_topic_list, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val news = data[position]
        val h = holder
        h.user_pic.url = news.user_photo
        h.user_name.text = news.user_nick
        h.pic.url = news.pic
        h.title.text = news.title
        h.comment_btn.text = news.comment_count.toString()
        h.time.text = news.created_at

        if (news.unread) {
            h.container.backgroundColor = ResourceUtil.getColor(R.color.colorUnread)
        } else {
            h.container.backgroundColor = ResourceUtil.getColor(R.color.colorArticleBackground)
        }
        if (::onClickListener.isInitialized) h.itemView.onClick { onClickListener.onClick(news.id.toString()) }
    }

    interface OnClickListener {
        fun onClick(id: String)
    }

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val user_pic = view.findViewById<UrlImageView>(R.id.topic_user_pic)
        val user_name = view.findViewById<TextView>(R.id.topic_user_name)
        val pic = view.findViewById<UrlImageView>(R.id.topic_pic)
        val title = view.findViewById<TextView>(R.id.topic_title)
        val tag = view.findViewById<TextView>(R.id.topic_tag)
        val comment_btn = view.findViewById<TextView>(R.id.topic_comment_btn)
        val time = view.findViewById<TextView>(R.id.topic_time)
        val container = view.find<ConstraintLayout>(R.id.topic_container)
    }
}
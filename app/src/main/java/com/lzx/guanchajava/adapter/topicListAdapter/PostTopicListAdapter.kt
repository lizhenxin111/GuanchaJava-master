package com.lzx.guanchajava.adapter.topicListAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.postTopicList.Data
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.sdk27.coroutines.onClick

class PostTopicListAdapter : RecyclerView.Adapter<PostTopicListAdapter.Holder>() {

    var data = mutableListOf<Data>()
    lateinit var onClickListener : OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_topic_list, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val news = data[position]
        holder.user_pic.url = news.user_photo
        holder.user_pic.ifCircle = true
        holder.user_name.text = news.user_nick
        holder.pic.url = news.pic
        holder.title.text = news.title
        holder.tag.text = news.topics[0].topic_name
        holder.comment_btn.text = news.comment_count.toString()
        holder.time.text = news.created_at
        if (::onClickListener.isInitialized) holder.itemView.onClick { onClickListener.onClick(news.id.toString()) }
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
    }
}
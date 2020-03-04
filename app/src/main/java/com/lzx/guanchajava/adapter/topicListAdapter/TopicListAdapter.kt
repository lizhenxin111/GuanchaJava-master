package com.lzx.guanchajava.adapter.topicListAdapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.topicList.TopicItem
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.BaseRecyclerViewAdapter
import com.lzx.guanchajava.view.widget.UrlImageView
import com.orhanobut.logger.Logger
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class TopicListAdapter(val onClickListener: OnClickListener) : BaseRecyclerViewAdapter<TopicItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_topic_list, parent, false))
    }

    override fun getItemCount(): Int = data.size

    interface OnClickListener {
        fun onClick(id: String, type: String)
        fun onLongClick(imageUtil: String, ancherView: View)
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val news = data[position]
        val h = holder as Holder
        h.user_pic.ifCircle = true
        h.user_pic.url = news.user_photo
        h.user_name.text = news.user_nick
        h.pic.apply {
            url = news.pic
            onLongClick {
                onClickListener.onLongClick(news.pic, h.pic)
            }
            onClick {
                Logger.d("${news.id.toString()}      ${news.code_type.toString()}")
                onClickListener.onClick(news.id.toString(), news.code_type.toString())
            }
        }
        h.title.text = news.title
        h.tag.text = news.topics[0].topic_name
        h.comment_btn.text = news.comment_count.toString()
        h.time.text = news.created_at

        h.itemView.onClick {
            onClickListener.onClick(news.id.toString(), news.code_type.toString())
        }
    }
}
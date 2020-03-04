package com.lzx.guanchajava.adapter.newsListAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.newsList.News
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class Type2(override var onClickListener: INewsType.OnClickListener) : INewsType {

    override fun isForViewType(news: News): Boolean {
        return news.show_type == 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_type_2, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, news: News) {
        val h = holder as Holder
        h.pic.apply {
            url = news.pic
            onLongClick {
                onClickListener.onLongClick(news.pic, h.pic)
            }
            onClick {
                onClickListener.onClick(news.id, news.news_type.toString())
            }
        }
        h.itemView.onClick {
            onClickListener.onClick(news.id, news.news_type.toString())
        }
        h.title.text = news.title
        if (news.special != null) h.tag.apply {
            text = news.special.name
            onClick {
                onClickListener.onTagClick(news.special.id)
            }
        }
        h.comment_sum.text = news.comment_num.toString()
        h.time.text = news.news_time
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.item_title)
        val pic = view.findViewById<UrlImageView>(R.id.item_pic)
        val tag = view.findViewById<TextView>(R.id.item_tag)
        val comment_sum = view.findViewById<TextView>(R.id.item_comment_sum)
        val time = view.findViewById<TextView>(R.id.item_time)
    }
}
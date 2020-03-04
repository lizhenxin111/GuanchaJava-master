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

class Type5(override var onClickListener: INewsType.OnClickListener) : INewsType {


    override fun isForViewType(news: News): Boolean {
        return news.show_type == 4
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_type_5, parent, false))
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
        h.commentSum.text = news.comment_num.toString()
        if (news.special != null) h.tag.apply {
            text = news.special.name
            onClick {
                onClickListener.onTagClick(news.special.id)
            }
        }
        h.time.text = news.news_time
        //h.author.text = news.author?.name
        //h.authorDescription.text = news.author?.description

    }


    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val pic = view.findViewById<UrlImageView>(R.id.item_pic)
        val title = view.findViewById<TextView>(R.id.item_title)
        val commentSum = view.findViewById<TextView>(R.id.item_comment_sum)
        val tag = view.findViewById<TextView>(R.id.item_tag)
        val time = view.findViewById<TextView>(R.id.item_time)
        //val author = view.findViewById<TextView>(R.id.item_author)
        //val authorDescription = view.findViewById<TextView>(R.id.item_author_description)
    }
}
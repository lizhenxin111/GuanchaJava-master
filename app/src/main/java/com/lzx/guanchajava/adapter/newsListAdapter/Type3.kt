package com.lzx.guanchajava.adapter.newsListAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.bean.newsList.News
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.TagsActivity
import com.lzx.guanchajava.util.JumpUtil
import com.lzx.guanchajava.util.SettingUtil
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class Type3(override var onClickListener: INewsType.OnClickListener) : INewsType {

    override fun isForViewType(news: News): Boolean {
        return news.show_type == 6
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_type_3, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, news: News) {
        val h = holder as Holder
        if (SettingUtil.isNoPic()) {
            h.pic.visibility = View.GONE
            h.title.onClick {
                JumpUtil.startActivity(h.itemView.context, TagsActivity::class.java, TAG.ACTIVITY_TAGS to news.id)
            }
        }
        else {
            h.pic.url = news.pic
        }
        h.pic.apply {
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
        /*h.itemView.onClick {
            JumpUtil.startActivity(h.itemView.context, TagsActivity::class.java, TAG.ACTIVITY_TAGS to news.id)
        }*/
    }


    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val pic = view.findViewById<UrlImageView>(R.id.item_pic)
        val title = view.findViewById<TextView>(R.id.item_title)
    }
}
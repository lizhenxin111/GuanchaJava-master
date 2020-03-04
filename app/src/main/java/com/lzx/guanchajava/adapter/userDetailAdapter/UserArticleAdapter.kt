package com.lzx.guanchajava.adapter.userDetailAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.userDetail.article.Item
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * 发表和点赞的文章的列表，皆为风闻
 */
class UserArticleAdapter : RecyclerView.Adapter<UserArticleAdapter.Holder>() {

    var data = mutableListOf<Item>()
    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_type_2, p0, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val d = data[p1]
        p0.item_title.text = d.title
        p0.item_tag.text
        p0.item_comment_sum.text = d.comment_count.toString()
        p0.item_time.text = d.created_at
        if (d.pic != null) {
            p0.item_pic.url = d.pic
        } else {
            p0.item_pic.visibility = View.GONE
        }
        if (::onClickListener.isInitialized) {
            p0.itemView.onClick {
                onClickListener.onClick(d.id.toString())
            }
        }
    }

    interface OnClickListener {
        fun onClick(id: String)
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val item_title = view.findViewById<TextView>(R.id.item_title)
        val item_pic = view.findViewById<UrlImageView>(R.id.item_pic)
        val item_tag = view.findViewById<TextView>(R.id.item_tag)
        val item_comment_sum = view.findViewById<TextView>(R.id.item_comment_sum)
        val item_time = view.findViewById<TextView>(R.id.item_time)
    }
}
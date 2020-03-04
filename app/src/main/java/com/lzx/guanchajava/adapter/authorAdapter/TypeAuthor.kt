package com.lzx.guanchajava.adapter.authorAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.author.Item
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.widget.UrlImageView

class TypeAuthor : IAuthor {
    override fun isForViewType(item: Item): Boolean {
        return item.id != "LABEL"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_author, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, item: Item) {
        val h = holder as Holder
        h.pic.url = item.pic
        h.name.text = item.name
        h.des.text = item.summary
        h.article.text = "文章：${item.article_count}"
    }


    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val pic = view.findViewById<UrlImageView>(R.id.author_pic)
        val name = view.findViewById<TextView>(R.id.author_name)
        val des = view.findViewById<TextView>(R.id.author_des)
        val article = view.findViewById<TextView>(R.id.author_article)
    }
}
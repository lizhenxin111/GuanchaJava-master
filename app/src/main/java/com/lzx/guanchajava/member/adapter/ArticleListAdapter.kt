package com.lzx.guanchajava.member.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.R
import com.lzx.guanchajava.member.bean.Article
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class ArticleListAdapter(val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<ArticleListAdapter.Holder>(){
    var data = mutableListOf<Article>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_article_list, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val a = data[position]
        holder.title.apply {
            text = a.article_title
            onClick {
                onItemClickListener.onTitleClick(a)
            }
        }
        holder.show.onClick {
            onItemClickListener.onButtonClick(a)
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.find<TextView>(R.id.article_title)
        val show = view.find<ImageButton>(R.id.article_show)
    }

    interface OnItemClickListener {
        fun onTitleClick(note: Article)

        fun onButtonClick(note: Article)
    }
}
package com.lzx.guanchajava.adapter.authorAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.db.DBAuthor
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class AuthorHistoryAdapter : RecyclerView.Adapter<AuthorHistoryAdapter.Holder>() {

    var data = mutableListOf<DBAuthor>()
    lateinit var onClickListener: OnClickListener
    lateinit var onLongClickListener: OnLongClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_author_history, p0, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val d = data[p1]
        p0.pic.url = d.pic
        if (::onClickListener.isInitialized && data.isNotEmpty()) {
            p0.itemView.onClick { onClickListener.onClick(d) }
        }
        if (::onLongClickListener.isInitialized && data.isNotEmpty()) {
            p0.itemView.onLongClick { onLongClickListener.onLongClick(p0.itemView, d) }
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val pic = view.findViewById<UrlImageView>(R.id.author_pic)
    }

    interface OnClickListener {
        fun onClick(item: DBAuthor)
    }

    interface OnLongClickListener {
        fun onLongClick(view: View, item: DBAuthor)
    }
}
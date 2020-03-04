package com.lzx.guanchajava.adapter.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.db.DBHistory
import com.lzx.guanchajava.R
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class NewsHistoryAdapter(val onClickListener: OnClickListener, val onLongClickListener: OnLongClickListener) : RecyclerView.Adapter<NewsHistoryAdapter.Holder>() {

    var data = mutableListOf<DBHistory>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_collection, p0, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val d = data[p1]
        p0.title.text = d.title
        p0.time.text = "${d.open_time} 至 ${d.close_time}"
        p0.itemView.onClick {
            onClickListener.onClick(d.article_id, d.article_type)
        }
        p0.itemView.onLongClick {
            onLongClickListener.onLongClick(d.article_id)
        }
        p0.comment.visibility = View.GONE
    }

    interface OnClickListener {
        fun onClick(id: String, type: String)
    }

    interface OnLongClickListener {
        fun onLongClick(articleId: String)
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.find<AppCompatTextView>(R.id.collection_title)
        val time = view.find<AppCompatTextView>(R.id.collection_time)
        val comment = view.find<AppCompatTextView>(R.id.collection_comment_sum)
    }
}
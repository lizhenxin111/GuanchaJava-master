package com.lzx.guanchajava.adapter.authorAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.authorDetail.Data
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class AuthorDetailAdapter(val onClickListener: OnClickListener) : RecyclerView.Adapter<AuthorDetailAdapter.Holder>() {


    lateinit var data: List<Data>

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AuthorDetailAdapter.Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_type_2, p0, false))
    }

    override fun getItemCount(): Int = if (::data.isInitialized) data.size else 0

    override fun onBindViewHolder(holder: AuthorDetailAdapter.Holder, p1: Int) {
        val d = data[p1]
        holder.pic.apply {
            url = d.preview_n
            onLongClick {
                onClickListener.onLongClick(d.preview_n, holder.pic)
            }
            onClick {
                onClickListener.onClick(d.id, d.type)
            }
        }
        holder.title.text = d.title
        holder.tag.text = d.special[0].name
        holder.comment_sum.text = d.comments
        holder.time.text = d.news_time

        holder.itemView.onClick { onClickListener.onClick(d.id, d.type) }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.item_title)
        val pic = view.findViewById<UrlImageView>(R.id.item_pic)
        val tag = view.findViewById<TextView>(R.id.item_tag)
        val comment_sum = view.findViewById<TextView>(R.id.item_comment_sum)
        val time = view.findViewById<TextView>(R.id.item_time)
    }

    interface OnClickListener {
        fun onClick(id: String, type: String)
        fun onLongClick(imageUrl: String, ancherView: View)
    }
}
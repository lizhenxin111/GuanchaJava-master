package com.lzx.guanchajava.adapter.recomendAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.recomend.post.Recommend
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.recomendAdapter.PostRecommendAdapter.Holder
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class PostRecommendAdapter(val onClickListener: OnClickListener) : RecyclerView.Adapter<Holder>() {

    var data = mutableListOf<Recommend>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
            Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_type_2, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val d = data[position]
        holder.title.text = d.title
        holder.pic.apply {
            url = d.pic
            onClick {
                onClickListener.onClick(d.id.toString(), d.code_type.toString())
            }
            onLongClick {
                onClickListener.onLongClick(d.pic, holder.pic)
            }
        }
        holder.comment_sum.visibility = View.GONE
        holder.itemView.onClick {
            onClickListener.onClick(d.id.toString(), d.code_type.toString())
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.find<TextView>(R.id.item_title)
        val pic = view.find<UrlImageView>(R.id.item_pic)
        val comment_sum = view.find<TextView>(R.id.item_comment_sum)
    }
}
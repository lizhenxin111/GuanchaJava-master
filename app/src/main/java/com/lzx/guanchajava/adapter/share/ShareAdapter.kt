package com.lzx.guanchajava.adapter.share

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class ShareAdapter(val data: List<Int>, val titleList: List<String>, val onClickListener: OnClickListener): RecyclerView.Adapter<ShareAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
            Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_share, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.pic.backgroundDrawable = App.context.getDrawable(data[position])
        holder.title.text = titleList[position]
        holder.itemView.onClick { onClickListener.onClick(position) }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val pic = view.find<UrlImageView>(R.id.share_pic)
        val title = view.find<TextView>(R.id.share_title)
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }
}
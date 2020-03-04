package com.lzx.guanchajava.adapter.userDetailAdapter.collect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.userDetail.collection.Item
import com.lzx.guanchajava.R
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * 用户收藏文章的列表。code_type==1:新闻；code_type==2:风闻
 */
class UserCollectionAdapter(val onClickListener: OnClickListener) : RecyclerView.Adapter<UserCollectionAdapter.Holder>() {

    var data = mutableListOf<Item>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_collection, p0, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val d = data[p1]
        p0.title.text = d.title
        p0.time.text = d.action_created_at
        p0.comment_sum.text = d.comment_count.toString()
        p0.itemView.onClick {
            onClickListener.onClick(d.code_id.toString(), d.code_type)
        }
    }

    interface OnClickListener {
        fun onClick(id: String, type: Int)
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.find<AppCompatTextView>(R.id.collection_title)
        val time = view.find<AppCompatTextView>(R.id.collection_time)
        val comment_sum = view.find<AppCompatTextView>(R.id.collection_comment_sum)
    }
}
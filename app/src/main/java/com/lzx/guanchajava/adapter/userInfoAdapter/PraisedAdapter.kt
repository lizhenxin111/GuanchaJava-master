package com.lzx.guanchajava.adapter.userInfoAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.userInfo.praised.Data
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class PraisedAdapter : RecyclerView.Adapter<PraisedAdapter.Holder>() {

    var data = mutableListOf<Data>()
    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_praised, p0, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val d = data[p1]
        p0.title.text = d.start + d.action
        p0.content.text = d.content
        p0.time.text = d.created_at

        if (!d.is_read) {
            p0.container.backgroundColor = ResourceUtil.getColor(R.color.colorUnread)
        }else {
            p0.container.backgroundColor = ResourceUtil.getColor(R.color.colorArticleBackground)
        }

        if (::onClickListener.isInitialized) p0.itemView.onClick { onClickListener.onClick(d.post_id.toString(), d.post_type.toString(), d.code_id) }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val content = view.find<TextView>(R.id.praised_content)
        val time = view.find<TextView>(R.id.praised_time)
        val title = view.find<TextView>(R.id.praised_title)
        val container = view.find<ConstraintLayout>(R.id.praised_container)
    }

    interface OnClickListener {
        fun onClick(postId: String, postType: String, codeId: String)
    }
}
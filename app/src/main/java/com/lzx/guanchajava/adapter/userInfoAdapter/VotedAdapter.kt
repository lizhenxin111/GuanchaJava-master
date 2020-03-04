package com.lzx.guanchajava.adapter.userInfoAdapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.userInfo.praised.Data
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.App
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find

class VotedAdapter : RecyclerView.Adapter<VotedAdapter.Holder>() {

    var data = mutableListOf<Data>()

    override fun onCreateViewHolder(p0: ViewGroup, position: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_praised, p0, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val d = data[position]
        if (!d.is_read) {
            holder.container.backgroundColor =
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) App.context.getColor(R.color.colorUnread)
                    else App.context.resources.getColor(R.color.colorUnread)
        }
        holder.title.text = d.start + d.action
        holder.content.text = d.content
        holder.time.text = d.created_at
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val content = view.find<TextView>(R.id.praised_content)
        val time = view.find<TextView>(R.id.praised_time)
        val title = view.find<TextView>(R.id.praised_title)
        val container = view.find<ConstraintLayout>(R.id.praised_container)
    }

}
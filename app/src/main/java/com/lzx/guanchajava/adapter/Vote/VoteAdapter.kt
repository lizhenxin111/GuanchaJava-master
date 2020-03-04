package com.lzx.guanchajava.adapter.Vote

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.topic.VoteOption
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.App
import org.jetbrains.anko.textColor

class VoteAdapter : RecyclerView.Adapter<VoteAdapter.Holder>() {
    var data = mutableListOf<VoteOption>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_vote, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val d = data[position]
        if (d.has_voted && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.title.textColor = App.context.getColor(R.color.colorPrimary)
            val p = holder.title.paint
            p.isFakeBoldText = true
        }
        holder.title.text = d.option_name
        holder.progress.apply {
            progress = d.vote_nums.toInt()
            max = d.vote_sum
        }
        val rate = String.format("%2f", d.vote_nums.toFloat() / d.vote_sum.toFloat())
        holder.num.text = "${d.vote_nums}票(${rate}%)"
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.vite_title)
        val progress = view.findViewById<ContentLoadingProgressBar>(R.id.vote_progress)
        val num = view.findViewById<TextView>(R.id.vote_num)
    }
}
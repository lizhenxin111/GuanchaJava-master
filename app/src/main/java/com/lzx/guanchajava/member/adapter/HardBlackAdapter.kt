package com.lzx.guanchajava.member.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.lzx.guanchajava.R
import com.lzx.guanchajava.member.bean.DBHardBlack
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class HardBlackAdapter(val onButtonClickAdapter: OnButtonClickListener) : RecyclerView.Adapter<HardBlackAdapter.Holder>(){
    val data = mutableListOf<DBHardBlack>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_hard_lack, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val d = data[position]
        holder.nick.apply {
            text = d.user_nick
        }
        holder.btn.apply {
            text = if (d.blacked) "移除" else "拉黑"
            onClick {
                onButtonClickAdapter.onButtonClick(holder.btn, d)
            }
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val nick = view.find<TextView>(R.id.black_nick)
        val btn = view.find<MaterialButton>(R.id.black_button)
    }

    interface OnButtonClickListener {
        fun onButtonClick(btn: MaterialButton, user: DBHardBlack)
    }
}
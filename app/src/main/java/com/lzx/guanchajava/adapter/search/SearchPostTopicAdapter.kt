package com.lzx.guanchajava.adapter.search

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.chip.Chip
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.operate.attendPostTopic.AttendPostTopic
import com.lzx.guanchajava.POJO.bean.search.postTopic.Item
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.BaseRecyclerViewAdapter
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.util.LoginUtil
import com.lzx.guanchajava.util.ResourceUtil
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk27.coroutines.onClick

class SearchPostTopicAdapter(val onClickListener: OnClickListener) : BaseRecyclerViewAdapter<Item>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_user, parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as Holder
        val d = data[position]
        h.pic.url = d.topic_img
        h.name.text = d.topic_name
        h.profile.text = "文章: ${d.post_nums}   关注: ${d.attention_nums}"

        if (d.has_attention) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                h.attention.chipBackgroundColor = App.context.getColorStateList(R.color.colorPrimary)
            }
            h.attention.tag = 1
            h.attention.text = "已关注"
        }
        h.attention.onClick {
            if (!LoginUtil.hasToken()) {
                Toast.makeText(App.context, "请登录", Toast.LENGTH_SHORT).show()
                return@onClick
            }
            Fuel.post(Api.attentionPostTopic(), listOf("topic_id" to d.topic_id)).responseObject(AttendPostTopic.Deserializer()) { request, response, result ->
                val (data, error) = result
                if (error != null) {
                    App.context.longToast(ResourceUtil.getString(R.string.str_no_network))
                } else {
                    val r = result.get().data.action
                    if (r == "set") attend(h.attention)
                    else unAttend(h.attention)
                }
            }
        }

        h.itemView.onClick { onClickListener.onClick(d) }
    }

    private fun attend(chip: Chip) {
        chip.tag = 1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            chip.chipBackgroundColor = App.context.getColorStateList(R.color.colorPrimary)
        }
        chip.text = "已关注"
        Toast.makeText(App.context, "已关注", Toast.LENGTH_SHORT).show()
    }

    private fun unAttend(chip: Chip) {
        chip.tag = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            chip.chipBackgroundColor = App.context.getColorStateList(R.color.colorArticleBackground)
        }
        chip.text = "关注"
        Toast.makeText(App.context, "取消关注", Toast.LENGTH_SHORT).show()
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val pic = view.findViewById<UrlImageView>(R.id.user_pic)
        val name = view.findViewById<AppCompatTextView>(R.id.user_name)
        val profile = view.findViewById<AppCompatTextView>(R.id.user_profile)
        val attention = view.findViewById<Chip>(R.id.user_attention)
    }

    interface OnClickListener {
        fun onClick(item: Item)
    }
}
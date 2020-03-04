package com.lzx.guanchajava.adapter.newsListAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzx.guanchajava.POJO.bean.newsList.News

class NewsListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var types: List<INewsType>
    var data = mutableListOf<News>()
    lateinit var onClickListener: OnClickListener

    override fun getItemViewType(position: Int) : Int{
        var news = data[position]
        for (x in types) {
            if (x.isForViewType(news)) return types.indexOf(x)
        }
        throw Exception("NewsListAdapter: 未找到合适的适配器")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return types[viewType].onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = holder.itemViewType
        val news = data[position]
        types[type].onBindViewHolder(holder, position, news)
    }

    interface OnClickListener {
        fun onClick(type: Int, id: String)
    }
}
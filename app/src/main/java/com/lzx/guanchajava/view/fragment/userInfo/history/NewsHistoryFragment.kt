package com.lzx.guanchajava.view.fragment.userInfo.history

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lzx.guanchajava.view.activity.NewsActivity
import com.lzx.guanchajava.view.activity.PostActivity
import com.lzx.guanchajava.adapter.history.NewsHistoryAdapter
import com.lzx.guanchajava.db.HistoryDB
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.util.JumpUtil

class NewsHistoryFragment : BaseListFragment<NewsHistoryAdapter>() {
    override fun getAdapter(): NewsHistoryAdapter = NewsHistoryAdapter(onClickListener, onLongClickListener)

    override fun requestData(mAdapter: NewsHistoryAdapter, mSwipe: SwipeRefreshLayout) {
        mAdapter.data.clear()
        mAdapter.data.addAll(HistoryDB.queryHistory())
        mAdapter.notifyDataSetChanged()
        mSwipe.isRefreshing = false
    }

    private val onClickListener = object : NewsHistoryAdapter.OnClickListener {
        override fun onClick(id: String, type: String) {
            if (type == "1") JumpUtil.startActivity(activity!!, NewsActivity::class.java, "ID" to id, "CODETYPE" to type)
            else JumpUtil.startActivity(activity!!, PostActivity::class.java, "ID" to id, "CODETYPE" to type)
        }
    }

    private val onLongClickListener = object : NewsHistoryAdapter.OnLongClickListener {
        override fun onLongClick(articleId: String) {
            HistoryDB.deleteHistory(articleId)
            requestData(mAdapter, mSwipe)
        }
    }
}
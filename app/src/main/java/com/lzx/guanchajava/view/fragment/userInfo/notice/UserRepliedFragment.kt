package com.lzx.guanchajava.view.fragment.userInfo.notice

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.bean.userInfo.replied.Replied
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.NewsActivity
import com.lzx.guanchajava.view.activity.PostActivity
import com.lzx.guanchajava.adapter.userInfoAdapter.RepliedAdapter
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.support.v4.longToast

/**
 * 通知中被回复
 */
class UserRepliedFragment : BaseListFragment<RepliedAdapter>() {

    companion object {
        fun newInstance(url: String): UserRepliedFragment = UserRepliedFragment().apply {
            arguments = Bundle().apply { putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url) }
        }

        fun newInstance(url: String, loadNow: Boolean): UserRepliedFragment = UserRepliedFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
                putBoolean(TAG.TAG_FRAGMENT_NEWS_LIST_LOADNOW, loadNow)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun getAdapter(): RepliedAdapter = RepliedAdapter(fragmentManager!!).apply {
        onClickListener = object : RepliedAdapter.OnClickListener {
            override fun onClick(id: Int, type: Int) {
                if (type == 1) goto(NewsActivity::class.java, "ID" to id.toString(), "CODETYPE" to type.toString())
                else if (type == 2) goto(PostActivity::class.java, "ID" to id.toString(), "CODETYPE" to type.toString())
            }
        }
    }

    override fun requestData(adapter: RepliedAdapter, swipe: SwipeRefreshLayout) {
        Fuel.get(mApi + 1).responseObject(Replied.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
                swipe.isRefreshing = false
            } else {
                adapter.data = result.get().data.comments
                adapter.notifyDataSetChanged()
                swipe.isRefreshing = false
            }
        }
    }

    override fun requestMore(mAdapter: RepliedAdapter) {
        Fuel.get(mApi + mPageNo++).responseObject(Replied.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val p1 = mAdapter.itemCount
                val data = result.get().data.comments
                if (data.size == 0) hasMore = false
                mAdapter.data.addAll(data)
                val p2 = mAdapter.itemCount
                mAdapter.notifyItemRangeInserted(p1, p2)
            }
        }
    }
}
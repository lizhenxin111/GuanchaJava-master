package com.lzx.guanchajava.view.fragment.userInfo.notice

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.userInfo.praised.Praised
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.userInfoAdapter.VotedAdapter
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.support.v4.longToast

class UserVotedFragment : BaseListFragment<VotedAdapter>() {

    companion object {
        fun newInstance(url: String) : UserVotedFragment = UserVotedFragment().apply {
            arguments = Bundle().apply{putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)}
        }

        fun newInstance(url: String, loadNow: Boolean): UserVotedFragment = UserVotedFragment().apply {
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

    override fun getAdapter(): VotedAdapter = VotedAdapter()

    override fun requestData(adapter: VotedAdapter, swipe: SwipeRefreshLayout) {
        Fuel.get(mApi + 1).responseObject(Praised.Deserializer()) { request, response, result ->
            val(data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
                swipe.isRefreshing = false
            } else {
                adapter.data = result.get().data
                adapter.notifyDataSetChanged()
                swipe.isRefreshing = false
                Api.clearNotice("vote")
            }
        }
    }

    override fun requestMore(mAdapter: VotedAdapter) {
        Fuel.get(mApi + mPageNo++).responseObject(Praised.Deserializer()) { request, response, result ->
            val(data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val p1 = mAdapter.itemCount
                val data = result.get().data
                if (data.size == 0) hasMore = false
                mAdapter.data.addAll(data)
                val p2 = mAdapter.itemCount
                mAdapter.notifyItemRangeInserted(p1, p2)
            }
        }
    }
}
package com.lzx.guanchajava.view.fragment.userInfo.notice

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.userInfo.followed.Followed
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.UserDetailActivity
import com.lzx.guanchajava.adapter.userInfoAdapter.FollowedAdapter
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.startActivity

class FollowedFragment : BaseListFragment<FollowedAdapter>() {


    companion object {
        fun newInstance(url: String) : FollowedFragment = FollowedFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
            }
        }
    }

    override fun getAdapter(): FollowedAdapter = FollowedAdapter().apply {
        onClickListener = object : FollowedAdapter.OnClickListener {
            override fun onClick(id: String) {
                startActivity<UserDetailActivity>("UID" to id)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun requestData(adapter: FollowedAdapter, swipe: SwipeRefreshLayout) {
        Fuel.get(mApi + 1).responseObject(Followed.Deserializer()) { _, response, result ->
            val(data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
                swipe.isRefreshing = false
            } else {
                adapter.data = result.get().data
                adapter.notifyDataSetChanged()
                swipe.isRefreshing = false
                Api.clearNotice("attention")
            }
        }
    }

    override fun requestMore(mAdapter: FollowedAdapter) {
        Fuel.get(mApi + mPageNo++).responseObject(Followed.Deserializer()) { _, response, result ->
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
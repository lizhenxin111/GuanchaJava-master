package com.lzx.guanchajava.view.fragment.userInfo

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.userDetail.subscription.Data
import com.lzx.guanchajava.POJO.bean.userDetail.subscription.UserSubscription
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.PostActivity
import com.lzx.guanchajava.adapter.userDetailAdapter.UserSubscriptionAdapter
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.support.v4.longToast

class UserSubscriptionFragment : BaseListFragment<UserSubscriptionAdapter>() {

    companion object {
        fun newInstance(url: String) = UserSubscriptionFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
            }
        }

        fun newInstance(url: String, loadNow: Boolean): UserSubscriptionFragment = UserSubscriptionFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
                putBoolean(TAG.TAG_FRAGMENT_NEWS_LIST_LOADNOW, loadNow)
            }
        }
    }

    override fun getAdapter(): UserSubscriptionAdapter = UserSubscriptionAdapter().apply {
        onClickListener = object : UserSubscriptionAdapter.OnClickListener {
            override fun onClick(id: String) {
                goto(PostActivity::class.java,"ID" to id, "CODETYPE" to "2")
            }
        }
    }

    override fun requestData(adapter: UserSubscriptionAdapter, swipe: SwipeRefreshLayout) {
        Fuel.get(mApi).responseObject(UserSubscription.Deserializer()) { _, response, result ->
            val(data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
                swipe.isRefreshing = false
            } else {
                if (response.statusCode == 200) {
                    adapter.data = result.get().data as MutableList<Data>
                    adapter.notifyDataSetChanged()
                    swipe.isRefreshing = false
                    Api.clearNotice("subscription")
                } else {
                    longToast("网络出现问题，请稍后再试")
                }
            }
        }
    }

    override fun requestMore(mAdapter: UserSubscriptionAdapter) {
        Fuel.get(mApi + mPageNo++).responseObject(UserSubscription.Deserializer()) { _, response, result ->
            val(data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val p1 = mAdapter.itemCount
                val data = result.get().data
                if (data.isEmpty()) hasMore = false
                mAdapter.data.addAll(data)
                val p2 = mAdapter.itemCount
                mAdapter.notifyItemRangeInserted(p1, p2)
            }
        }
    }

    override fun getLayoutManager(c: Context): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }
}
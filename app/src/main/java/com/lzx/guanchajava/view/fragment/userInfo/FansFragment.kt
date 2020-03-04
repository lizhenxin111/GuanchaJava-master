package com.lzx.guanchajava.view.fragment.userInfo

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.bean.userInfo.attentionUser.AttentionUser
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.UserDetailActivity
import com.lzx.guanchajava.adapter.userInfoAdapter.AttentionUserAdapter
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.startActivity

class FansFragment : BaseListFragment<AttentionUserAdapter>() {

    companion object {
        fun newInstance(url: String): FansFragment = FansFragment().apply {
            arguments = Bundle().apply { putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url) }
        }   //type=1:关注；type=2:粉丝

        fun newInstance(url: String, loadNow: Boolean): FansFragment = FansFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
                putBoolean(TAG.TAG_FRAGMENT_NEWS_LIST_LOADNOW, loadNow)
            }
        }
    }

    override fun getAdapter(): AttentionUserAdapter = AttentionUserAdapter().apply {
        onClickListener = object : AttentionUserAdapter.OnClickListener {
            override fun onClick(uid: String) {
                startActivity<UserDetailActivity>("UID" to uid)
            }
        }
    }

    override fun requestData(adapter: AttentionUserAdapter, swipe: SwipeRefreshLayout) {
        Fuel.get(mApi + 1).responseObject(AttentionUser.Deserializer()) { _, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
                swipe.isRefreshing = false
            } else {
                adapter.data = result.get().data
                adapter.notifyDataSetChanged()
                swipe.isRefreshing = false
            }
        }
    }

    override fun requestMore(mAdapter: AttentionUserAdapter) {
        Fuel.get(mApi + mPageNo++).responseObject(AttentionUser.Deserializer()) { _, response, result ->
            val (data, error) = result
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
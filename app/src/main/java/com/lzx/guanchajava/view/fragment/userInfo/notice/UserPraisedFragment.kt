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
import com.lzx.guanchajava.view.activity.NewsActivity
import com.lzx.guanchajava.view.activity.PostActivity
import com.lzx.guanchajava.adapter.userInfoAdapter.PraisedAdapter
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.view.fragment.userDetail.collection.UserCollectionFragment
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.startActivity

class UserPraisedFragment : BaseListFragment<PraisedAdapter>() {

    companion object {
        fun newInstance(url: String) : UserPraisedFragment = UserPraisedFragment().apply {
            arguments = Bundle().apply{putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)}
        }

        fun newInstance(url: String, loadNow: Boolean): UserCollectionFragment = UserCollectionFragment().apply {
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

    override fun getAdapter(): PraisedAdapter = PraisedAdapter().apply {
        onClickListener = object : PraisedAdapter.OnClickListener {
            override fun onClick(postId: String, postType: String, codeId: String) {
                when (postType) {
                    "1" -> startActivity<NewsActivity>("ID" to postId, "CODETYPE" to postType)
                    "2" -> startActivity<PostActivity>("ID" to postId, "CODETYPE" to postType)
                    else -> startActivity<PostActivity>("ID" to codeId, "CODETYPE" to "2")
                }
            }
        }
    }

    override fun requestData(adapter: PraisedAdapter, swipe: SwipeRefreshLayout) {
        Fuel.get(mApi + 1).responseObject(Praised.Deserializer()) { request, response, result ->
            val(data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
                swipe.isRefreshing = false
            } else {
                adapter.data = result.get().data
                adapter.notifyDataSetChanged()
                swipe.isRefreshing = false
                Api.clearNotice("collection_praise")
            }
        }
    }

    override fun requestMore(mAdapter: PraisedAdapter) {
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
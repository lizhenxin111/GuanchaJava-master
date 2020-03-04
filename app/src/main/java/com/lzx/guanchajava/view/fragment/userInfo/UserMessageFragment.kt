package com.lzx.guanchajava.view.fragment.userInfo

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.userDetail.message.Item
import com.lzx.guanchajava.POJO.bean.userDetail.message.Message
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.UserMessageActivity
import com.lzx.guanchajava.adapter.userDetailAdapter.UserMessageAdapter
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.startActivity

class UserMessageFragment : BaseListFragment<UserMessageAdapter>() {
    companion object {
        fun newInstance(loadNow: Boolean) = UserMessageFragment().apply {
            arguments = Bundle().apply {
                putBoolean(TAG.TAG_FRAGMENT_NEWS_LIST_LOADNOW, loadNow)
            }
        }
    }

    override fun getAdapter(): UserMessageAdapter = UserMessageAdapter(onClickObj)

    override fun requestData(mAdapter: UserMessageAdapter, mSwipe: SwipeRefreshLayout) {
        Fuel.post(Api.getMessage(), listOf("page_no" to 1, "page_size" to 20)).responseObject(Message.Deserializer()) { request, response, result ->
            val(data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                mAdapter.data = result.get().data.items as MutableList<Item>
                mAdapter.notifyDataSetChanged()
                mSwipe.isRefreshing = false
            }
        }
    }

    override fun requestMore(mAdapter: UserMessageAdapter) {
        Fuel.post(Api.getMessage(), listOf("page_no" to mPageNo++, "page_size" to 20)).responseObject(Message.Deserializer()) { request, response, result ->
            val(data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val p1 = mAdapter.itemCount
                val data = result.get().data.items
                if (data.isNullOrEmpty()) {
                    hasMore = false
                } else {
                    mAdapter.data.addAll(data)
                    val p2 = mAdapter.itemCount
                    mAdapter.notifyItemRangeInserted(p1, p2)
                }
            }
        }
    }

    private val onClickObj = object : UserMessageAdapter.OnClickListener {
        override fun onClick(senderUid: String, senderNane: String) {
            startActivity<UserMessageActivity>("UID" to senderUid, "NAME" to senderNane)
        }
    }
}
package com.lzx.guanchajava.view.fragment.userDetail.collection

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.view.activity.NewsActivity
import com.lzx.guanchajava.view.activity.PostActivity
import com.lzx.guanchajava.adapter.userDetailAdapter.collect.UserCollectionAdapter
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.viewmodel.user.vmUserDetail
import org.jetbrains.anko.support.v4.startActivity

class UserCollectionFragment : BaseListFragment<UserCollectionAdapter>() {

    private val mNewsListViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
                .get(vmUserDetail::class.java)
    }

    companion object {
        fun newInstance(url: String): UserCollectionFragment = UserCollectionFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
            }
        }

        fun newInstance(url: String, loadNow: Boolean): UserCollectionFragment = UserCollectionFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
                putBoolean(TAG.TAG_FRAGMENT_NEWS_LIST_LOADNOW, loadNow)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mNewsListViewModel.userCollection.observe(viewLifecycleOwner, Observer {
            val p1 = mAdapter.itemCount
            if (it.isEmpty()) hasMore = false
            mAdapter.data.addAll(it)
            val p2 = mAdapter.itemCount
            mAdapter.notifyItemRangeInserted(p1, p2)
            mSwipe.isRefreshing = false
        })
    }

    override fun getAdapter(): UserCollectionAdapter = UserCollectionAdapter(clickObj)

    override fun requestData(mAdapter: UserCollectionAdapter, mSwipe: SwipeRefreshLayout) {
        mNewsListViewModel.getUserCollection(mApi + 1)
    }

    override fun requestMore(mAdapter: UserCollectionAdapter) {
        mNewsListViewModel.getUserCollection(mApi + mPageNo++)
    }

    private val clickObj = object : UserCollectionAdapter.OnClickListener {
        override fun onClick(id: String, type: Int) {
            when (type) {
                1 -> startActivity<NewsActivity>("ID" to id, "CODETYPE" to "1")
                2 -> startActivity<PostActivity>("ID" to id, "CODETYPE" to "2")
                else -> throw Exception("UserCollectionFragment：类型参数错误")
            }
        }
    }
}
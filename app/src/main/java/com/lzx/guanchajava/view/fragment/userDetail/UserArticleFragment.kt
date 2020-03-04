package com.lzx.guanchajava.view.fragment.userDetail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.view.activity.PostActivity
import com.lzx.guanchajava.adapter.userDetailAdapter.UserArticleAdapter
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.viewmodel.user.vmUserDetail
import org.jetbrains.anko.support.v4.startActivity

class UserArticleFragment : BaseListFragment<UserArticleAdapter>() {

    private val mNewsListViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
                .get(vmUserDetail::class.java)
    }

    companion object {
        fun newInstance(url: String): UserArticleFragment = UserArticleFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
            }
        }

        fun newInstance(url: String, loadNow: Boolean): UserArticleFragment = UserArticleFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
                putBoolean(TAG.TAG_FRAGMENT_NEWS_LIST_LOADNOW, loadNow)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mNewsListViewModel.userArticle.observe(viewLifecycleOwner, Observer {
            val p1 = mAdapter.itemCount
            if (it.size == 0) hasMore = false
            mAdapter.data.addAll(it)
            val p2 = mAdapter.itemCount
            mAdapter.notifyItemRangeInserted(p1, p2)
            mSwipe.isRefreshing = false
        })
    }

    override fun getAdapter(): UserArticleAdapter = UserArticleAdapter().apply {
        onClickListener = clickObj
    }

    override fun requestData(adapter: UserArticleAdapter, swipe: SwipeRefreshLayout) {
        mNewsListViewModel.getUserArticle(mApi + 1)
    }

    override fun requestMore(mAdapter: UserArticleAdapter) {
        mNewsListViewModel.getUserArticle(mApi + mPageNo++)
    }

    private val clickObj = object : UserArticleAdapter.OnClickListener {
        override fun onClick(id: String) {
            startActivity<PostActivity>("ID" to id, "CODETYPE" to "2")
        }
    }
}
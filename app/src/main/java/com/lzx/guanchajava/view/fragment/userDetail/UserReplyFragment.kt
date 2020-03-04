package com.lzx.guanchajava.view.fragment.userDetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.view.activity.NewsActivity
import com.lzx.guanchajava.view.activity.PostActivity
import com.lzx.guanchajava.adapter.userDetailAdapter.UserReplytAdapter
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.util.JumpUtil
import com.lzx.guanchajava.viewmodel.user.vmUserDetail
import org.jetbrains.anko.support.v4.toast

class UserReplyFragment : BaseListFragment<UserReplytAdapter>() {

    private val mNewsListViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
                .get(vmUserDetail::class.java)
    }

    companion object {
        fun newInstance(url: String): UserReplyFragment = UserReplyFragment().apply {
            arguments = Bundle().apply{putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)}
        }

        fun newInstance(url: String, loadNow: Boolean): UserReplyFragment = UserReplyFragment().apply {
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mNewsListViewModel.userReply.observe(viewLifecycleOwner, Observer {
            val p1 = mAdapter.itemCount
            if (it.size == 0) hasMore = false
            mAdapter.data.addAll(it)
            val p2 = mAdapter.itemCount
            mAdapter.notifyItemRangeInserted(p1, p2)
            mSwipe.isRefreshing = false
        })
    }

    override fun getAdapter(): UserReplytAdapter = UserReplytAdapter(childFragmentManager).apply {
        onClickListener = object : UserReplytAdapter.OnClickListener {
            override fun onClick(id: String, type: String) {
                if (type == "1") JumpUtil.startActivity(activity!!, NewsActivity::class.java, "ID" to id, "CODETYPE" to type)
                else if (type == "2")JumpUtil.startActivity(activity!!, PostActivity::class.java, "ID" to id, "CODETYPE" to type)
                else toast("未找到该文章")
            }
        }
    }

    override fun requestData(adapter: UserReplytAdapter, swipe: SwipeRefreshLayout) {
        mNewsListViewModel.getUserReply(mApi + 1)
    }

    override fun requestMore(mAdapter: UserReplytAdapter) {
        mNewsListViewModel.getUserReply(mApi + mPageNo++)
    }
}
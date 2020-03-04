package com.lzx.guanchajava.view.fragment.ItemListFragment

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.view.activity.PostActivity
import com.lzx.guanchajava.adapter.topicListAdapter.PostTopicListAdapter
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.viewmodel.news.vmPostTopicList
import org.jetbrains.anko.support.v4.startActivity

class PostTopicListFragment : BaseListFragment<PostTopicListAdapter>() {

    private val mTopicListViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
                .get(vmPostTopicList::class.java)
    }

    companion object {
        fun newInstance(url: String) = PostTopicListFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mTopicListViewModel.topicList.observe(viewLifecycleOwner, Observer {
            if (mPageNo == 1) {
                mAdapter.data = it
                mAdapter.notifyDataSetChanged()
                mSwipe.isRefreshing = false
                mPageNo = 2
            } else {
                val p1 = mAdapter.itemCount
                if (it.size == 0) hasMore = false
                mAdapter.data.addAll(it)
                val p2 = mAdapter.itemCount
                mAdapter.notifyItemRangeInserted(p1, p2)
            }
        })
    }

    override fun getAdapter(): PostTopicListAdapter = PostTopicListAdapter().apply {
        onClickListener = object : PostTopicListAdapter.OnClickListener {
            override fun onClick(id: String) {
                startActivity<PostActivity>("ID" to id, "CODETYPE" to "2")
            }
        }
    }

    override fun requestData(adapter: PostTopicListAdapter, swipe: SwipeRefreshLayout) {
        mTopicListViewModel.getTopicList(mApi + 1)
    }

    override fun requestMore(mAdapter: PostTopicListAdapter) {
        mTopicListViewModel.getTopicList(mApi + mPageNo++)
    }

    override fun getLayoutManager(c: Context): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }
}
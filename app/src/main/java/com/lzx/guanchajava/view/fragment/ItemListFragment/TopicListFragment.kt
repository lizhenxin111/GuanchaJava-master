package com.lzx.guanchajava.view.fragment.ItemListFragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.bean.topicList.TopicItem
import com.lzx.guanchajava.view.activity.NewsActivity
import com.lzx.guanchajava.view.activity.PostActivity
import com.lzx.guanchajava.adapter.topicListAdapter.TopicListAdapter
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.util.ImageUtils
import com.lzx.guanchajava.util.JumpUtil
import com.lzx.guanchajava.viewmodel.bottomNavigation.vmTopicList


open class TopicListFragment : BaseListFragment<TopicListAdapter>() {
    
    private val mTopicListViewModel by lazy { 
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
                .get(vmTopicList::class.java)
    }

    companion object {
        fun newInstance(url: String) = TopicListFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        mTopicListViewModel.topicListData.observe(viewLifecycleOwner, Observer {
            handleData(it.data.items)
        })
    }

    private fun handleData(data: MutableList<TopicItem>) {
        if (mPageNo == 1) {
            mAdapter.data = data
            mAdapter.notifyDataSetChanged()
            mPageNo = 2
        } else {
            val p1 = mAdapter.itemCount
            if (data.size == 0) hasMore = false
            mAdapter.data.addAll(data)
            val p2 = mAdapter.itemCount
            mAdapter.notifyItemRangeInserted(p1, p2)
        }
        mSwipe.isRefreshing = false
    }

    override fun requestData(adapter: TopicListAdapter, swipe: SwipeRefreshLayout) {
        mTopicListViewModel.loadData(mApi + 1)
    }

    override fun getAdapter(): TopicListAdapter = TopicListAdapter(onClickListener)

    private val onClickListener = object : TopicListAdapter.OnClickListener {
        override fun onClick(id: String, type: String) {
            if (type == "1") JumpUtil.startActivity(this@TopicListFragment.activity!!, NewsActivity::class.java, "ID" to id, "CODETYPE" to type)
            else JumpUtil.startActivity(this@TopicListFragment.activity!!, PostActivity::class.java, "ID" to id, "CODETYPE" to type)

        }

        override fun onLongClick(imageUtil: String, ancherView: View) {
            ImageUtils.saveImageWithPopup(imageUtil,ancherView)
        }
    }



    override fun requestMore(mAdapter: TopicListAdapter) {
        mTopicListViewModel.loadData(mApi + mPageNo++)
    }

    override fun getLayoutManager(c: Context): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }
}
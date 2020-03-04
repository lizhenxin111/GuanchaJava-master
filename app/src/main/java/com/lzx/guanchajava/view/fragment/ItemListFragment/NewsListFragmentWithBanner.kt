package com.lzx.guanchajava.view.fragment.ItemListFragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.view.activity.NewsActivity
import com.lzx.guanchajava.view.activity.PostActivity
import com.lzx.guanchajava.view.activity.TagsActivity
import com.lzx.guanchajava.adapter.newsListAdapter.*
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.util.ImageUtils
import com.lzx.guanchajava.util.JumpUtil
import com.lzx.guanchajava.viewmodel.bottomNavigation.vmNewsListBanner

/**
 * 带Banner的ListFragment, 财经、产经及其他
 */
class NewsListFragmentWithBanner : BaseListFragment<NewsListAdapter>()   {

    private val mNewsListViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
                .get(vmNewsListBanner::class.java)
    }

    companion object {
        fun newInstance(url: String): NewsListFragmentWithBanner = NewsListFragmentWithBanner().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
            }
        }

        fun newInstance(url: String, loadNow: Boolean): NewsListFragmentWithBanner = NewsListFragmentWithBanner().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
                putBoolean(TAG.TAG_FRAGMENT_NEWS_LIST_LOADNOW, loadNow)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mNewsListViewModel.newsListData.observe(viewLifecycleOwner, Observer {
            val data = it.data
            if (mPageNo == 1) {
                mAdapter.data.clear()
                if (!data.toutiao.isNullOrEmpty()) mAdapter.data.addAll(data.toutiao)
                mAdapter.data.addAll(data.items)
                mAdapter.notifyDataSetChanged()
                mPageNo = 2
            } else {
                val p1 = mAdapter.itemCount
                val data = data.items
                if (data.size == 0) hasMore = false
                mAdapter.data.addAll(data)
                val p2 = mAdapter.itemCount
                mAdapter.notifyItemRangeInserted(p1, p2)
            }
            mSwipe.isRefreshing = false
        })
    }


    override fun getAdapter(): NewsListAdapter = NewsListAdapter().apply {
        types = listOf(Type1(onTagClickObj), Type2(onTagClickObj), Type3(onTagClickObj), Type4(onTagClickObj), Type5(onTagClickObj))
        onClickListener = object : NewsListAdapter.OnClickListener {
            override fun onClick(type: Int, id: String) {
                when (type) {
                    1 -> {
                        goto(NewsActivity::class.java, "ID" to id, "CODETYPE" to "1")
                    }     //新闻
                    2 -> {
                        goto(PostActivity::class.java,"ID" to id, "CODETYPE" to "2")
                    }     //风闻
                    3 -> toTagsAct(id)      //标签
                    else -> {

                    }
                }
            }
        }
    }

    private val onTagClickObj = object : INewsType.OnClickListener {
        override fun onTagClick(id: String) {
            toTagsAct(id)
        }

        override fun onLongClick(imageUrl: String, ancherView: View) {
            ImageUtils.saveImageWithPopup(imageUrl, ancherView)
        }

        override fun onClick(id: String, type: String) {
            if (type == "1") JumpUtil.startActivity(this@NewsListFragmentWithBanner.activity!!, NewsActivity::class.java, "ID" to id, "CODETYPE" to type)
            else JumpUtil.startActivity(this@NewsListFragmentWithBanner.activity!!, PostActivity::class.java, "ID" to id, "CODETYPE" to type)
        }
    }

    private fun toTagsAct(id: String) {
        goto(TagsActivity::class.java, TAG.ACTIVITY_TAGS to id)
    }




    override fun requestData(adapter: NewsListAdapter, swipe: SwipeRefreshLayout) {
        mNewsListViewModel.loadData(mApi + 1)
    }

    override fun requestMore(mAdapter: NewsListAdapter) {
        mNewsListViewModel.loadData(mApi + mPageNo++)
    }
}
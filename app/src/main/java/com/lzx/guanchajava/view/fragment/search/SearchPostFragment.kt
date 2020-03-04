package com.lzx.guanchajava.view.fragment.search

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.bean.topicList.TopicItem
import com.lzx.guanchajava.view.activity.NewsActivity
import com.lzx.guanchajava.view.activity.PostActivity
import com.lzx.guanchajava.adapter.topicListAdapter.TopicListAdapter
import com.lzx.guanchajava.util.ImageUtils
import com.lzx.guanchajava.util.JumpUtil

class SearchPostFragment : BaseSearchFragment<TopicItem>()   {

    companion object {
        fun newInstance(url: String): SearchPostFragment = SearchPostFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
            }
        }
    }

    override fun getAdapter(): TopicListAdapter = TopicListAdapter(onClickListener)

    override fun requestData() {
        mSearchViewModel.searchPost(mApi + 1)
    }

    override fun requestMore() {
        mSearchViewModel.searchPost(mApi + mPageNo++)
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private val onClickListener = object : TopicListAdapter.OnClickListener {
        override fun onClick(id: String, type: String) {
            if (type == "1")
                JumpUtil.startActivity(this@SearchPostFragment.activity!!, NewsActivity::class.java, "ID" to id, "CODETYPE" to type)
            else
                JumpUtil.startActivity(this@SearchPostFragment.activity!!, PostActivity::class.java, "ID" to id, "CODETYPE" to type)
        }

        override fun onLongClick(imageUtil: String, ancherView: View) {
            ImageUtils.saveImageWithPopup(imageUtil,ancherView)
        }
    }
}
package com.lzx.guanchajava.view.fragment.search

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.TAG.ACTIVITY_TAGS
import com.lzx.guanchajava.POJO.bean.search.newsTopic.Item
import com.lzx.guanchajava.view.activity.TagsActivity
import com.lzx.guanchajava.adapter.search.SearchNewsTopicAdapter
import org.jetbrains.anko.support.v4.startActivity

class SearchNewsTopicFragment: BaseSearchFragment<Item>() {

    companion object {
        fun newInstance(url: String) : SearchNewsTopicFragment = SearchNewsTopicFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSearchViewModel.searchNewsTopic.observe(viewLifecycleOwner, Observer {
            notifyItemInsert(it)
        })
    }


    override fun getAdapter(): SearchNewsTopicAdapter = SearchNewsTopicAdapter(object : SearchNewsTopicAdapter.OnClickListener {
        override fun onClick(id: String) {
            startActivity<TagsActivity>(ACTIVITY_TAGS to id)
        }
    })

    override fun requestData() {
        mSearchViewModel.searchNewsTopic(mApi + 1)
    }

    override fun requestMore() {
        mSearchViewModel.searchNewsTopic(mApi + mPageNo++)
    }
}
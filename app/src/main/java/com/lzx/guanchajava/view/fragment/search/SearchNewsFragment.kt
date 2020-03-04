package com.lzx.guanchajava.view.fragment.search

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.bean.search.news.Item
import com.lzx.guanchajava.view.activity.NewsActivity
import com.lzx.guanchajava.adapter.search.SearchNewsAdapter
import org.jetbrains.anko.support.v4.startActivity

class SearchNewsFragment : BaseSearchFragment<Item>() {

    companion object {
        fun newInstance(url: String): SearchNewsFragment = SearchNewsFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
            }
        }
    }

    override fun getAdapter(): SearchNewsAdapter = SearchNewsAdapter(object : SearchNewsAdapter.OnClickListener {
        override fun onClick(id: String) {
            startActivity<NewsActivity>("ID" to id, "CODETYPE" to "1")
        }
    })

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSearchViewModel.searchNews.observe(viewLifecycleOwner, Observer {
            notifyItemInsert(it)
        })
    }

    override fun requestData() {
        mSearchViewModel.searchNews(mApi + 1)
    }

    override fun requestMore() {
        mSearchViewModel.searchNews(mApi + mPageNo++)
    }
}
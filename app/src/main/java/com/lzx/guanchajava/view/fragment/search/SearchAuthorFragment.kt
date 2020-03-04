package com.lzx.guanchajava.view.fragment.search

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.bean.search.author.Item
import com.lzx.guanchajava.view.activity.AuthorDetailActivity
import com.lzx.guanchajava.adapter.search.SearchAuthorAdapter
import org.jetbrains.anko.support.v4.startActivity

class SearchAuthorFragment: BaseSearchFragment<Item>() {

    companion object {
        fun newInstance(url: String) : SearchAuthorFragment = SearchAuthorFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mSearchViewModel.searchAuthor.observe(viewLifecycleOwner, Observer {
            notifyItemInsert(it)
        })
    }

    override fun getAdapter(): SearchAuthorAdapter = SearchAuthorAdapter(object : SearchAuthorAdapter.OnClickListener {
        override fun onClick(id: String) {
            startActivity<AuthorDetailActivity>("ID" to id)
        }
    })

    override fun requestData() {
        mSearchViewModel.searchAuthor(mApi + 1)
    }

    override fun requestMore() {
        mSearchViewModel.searchAuthor(mApi + mPageNo++)
    }
}
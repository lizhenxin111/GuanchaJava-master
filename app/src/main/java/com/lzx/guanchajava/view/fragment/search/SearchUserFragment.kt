package com.lzx.guanchajava.view.fragment.search

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.bean.search.user.Item
import com.lzx.guanchajava.view.activity.UserDetailActivity
import com.lzx.guanchajava.adapter.search.SearchUserAdapter
import org.jetbrains.anko.support.v4.startActivity

class SearchUserFragment : BaseSearchFragment<Item>() {

    companion object {
        fun newInstance(url: String): SearchUserFragment = SearchUserFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSearchViewModel.searchUser.observe(viewLifecycleOwner, Observer {
            notifyItemInsert(it)
        })
    }

    override fun getAdapter(): SearchUserAdapter = SearchUserAdapter(object : SearchUserAdapter.OnClickListener {
        override fun onClick(id: String) {
            startActivity<UserDetailActivity>("UID" to id)
        }
    })

    override fun requestData() {
        mSearchViewModel.searchUser(mApi + 1)
    }

    override fun requestMore() {
        mSearchViewModel.searchUser(mApi + mPageNo++)
    }
}
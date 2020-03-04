package com.lzx.guanchajava.view.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.adapter.FragmentsAdapter
import com.lzx.guanchajava.view.fragment.base.BaseNavFragment

class SearchNavFragment : BaseNavFragment() {
    private lateinit var WORD: String

    companion object {
        fun newInstance(word: String) : SearchNavFragment = SearchNavFragment().apply {
            arguments = Bundle().apply {
                putString("WORD", word)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        WORD = arguments!!.getString("WORD")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getTabTitles(): List<String> = listOf("新闻", "新闻专题", "风闻", "风闻话题", "用户", "作者")

    override fun setFragments(adapter: FragmentsAdapter) {
        adapter.fragments = listOf(SearchNewsFragment.newInstance(Api.searchNews(WORD)),
                SearchNewsTopicFragment.newInstance(Api.searchNewsTopic(WORD)),
                SearchPostFragment.newInstance(Api.searchPost(WORD)),
                SearchPostTopicFragment.newInstance(Api.searchPostTopic(WORD)),
                SearchUserFragment.newInstance(Api.searchUser(WORD)),
                SearchAuthorFragment.newInstance(Api.searchAuthor(WORD)))
        adapter.notifyDataSetChanged()
    }



    override fun canScroll(): Boolean = true

    override fun offscreenPageLimit(): Int = 5

    override fun tabMode(): Int = TabLayout.MODE_SCROLLABLE
}
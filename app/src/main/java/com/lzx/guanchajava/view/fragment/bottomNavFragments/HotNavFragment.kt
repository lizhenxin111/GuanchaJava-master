package com.lzx.guanchajava.view.fragment.bottomNavFragments

import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.adapter.FragmentsAdapter
import com.lzx.guanchajava.view.fragment.ItemListFragment.TopicListFragment
import com.lzx.guanchajava.view.fragment.base.BaseNavFragment

/**
 * BottomNavigationView中的风闻中的最热栏目
 */
class HotNavFragment : BaseNavFragment() {
    override fun getTabTitles(): List<String> = listOf("24小时", "3天", "7天", "3个月")

    override fun setFragments(adapter: FragmentsAdapter) {
        adapter.fragments = listOf(TopicListFragment.newInstance(Api.fwZr24h()),
                TopicListFragment.newInstance(Api.fwZr3()),
                TopicListFragment.newInstance(Api.fwZr7()),
                TopicListFragment.newInstance(Api.fwZr3m())
        )
        adapter.notifyDataSetChanged()
    }

    override fun offscreenPageLimit(): Int = 5
}
package com.lzx.guanchajava.view.fragment.bottomNavFragments
import com.google.android.material.tabs.TabLayout
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.adapter.FragmentsAdapter
import com.lzx.guanchajava.view.fragment.ItemListFragment.TopicListFragment
import com.lzx.guanchajava.view.fragment.base.BaseNavFragment

/**
 * BottomNavigationView中的风闻
 */
class TopicNavFragments : BaseNavFragment() {
    companion object {
        fun newsInstance(): TopicNavFragments {
            return TopicNavFragments()
        }
    }

    override fun getTabTitles(): List<String> {
        return listOf("最新发布", "专栏文章", "最新回复", "最热")
    }

    override fun setFragments(adapter: FragmentsAdapter) {
        adapter.fragments = listOf(TopicListFragment.newInstance(Api.fwZxfb()),
                TopicListFragment.newInstance(Api.fwZl()),
                TopicListFragment.newInstance(Api.fwZxhf()),
                HotNavFragment()
        )
        adapter.notifyDataSetChanged()
    }

    override fun offscreenPageLimit(): Int = 5
    override fun tabMode() = TabLayout.MODE_FIXED
}
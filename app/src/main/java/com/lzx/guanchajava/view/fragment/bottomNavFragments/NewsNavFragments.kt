package com.lzx.guanchajava.view.fragment.bottomNavFragments


import com.google.android.material.tabs.TabLayout
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.adapter.FragmentsAdapter
import com.lzx.guanchajava.view.fragment.ItemListFragment.NewsListFragmentWithBanner
import com.lzx.guanchajava.view.fragment.base.BaseNavFragment


/**
 * BottomNavigationView中的新闻
 */
class NewsNavFragments : BaseNavFragment() {


    companion object {
        fun newsInstance(): NewsNavFragments {
            return NewsNavFragments()
        }
    }

    override fun getTabTitles(): List<String> =
            listOf("要闻", "时评", "朋友圈", "财经", "产经","科技","汽车","国际","军事","视频","新时代","滚动")

    override fun setFragments(adapter: FragmentsAdapter) {
        adapter.fragments = listOf(NewsListFragmentWithBanner.newInstance(Api.yaowen()),
                NewsListFragmentWithBanner.newInstance(Api.shiping()),
                NewsListFragmentWithBanner.newInstance(Api.pyq()),
                NewsListFragmentWithBanner.newInstance(Api.caij()),
                NewsListFragmentWithBanner.newInstance(Api.chanj()),
                NewsListFragmentWithBanner.newInstance(Api.kej()),
                NewsListFragmentWithBanner.newInstance(Api.qic()),
                NewsListFragmentWithBanner.newInstance(Api.guoj()),
                NewsListFragmentWithBanner.newInstance(Api.junshi()),
                NewsListFragmentWithBanner.newInstance(Api.ship()),
                NewsListFragmentWithBanner.newInstance(Api.xinshd()),
                NewsListFragmentWithBanner.newInstance(Api.gund())
        )
        adapter.notifyDataSetChanged()
    }

    override fun offscreenPageLimit(): Int = 11


    override fun tabMode() = TabLayout.MODE_SCROLLABLE
}
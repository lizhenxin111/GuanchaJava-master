package com.lzx.guanchajava.view.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.tabs.TabLayout
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.FragmentsAdapter
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.view.widget.ScrollViewPager
import org.jetbrains.anko.find

abstract class BaseNavFragment : LazyFragment(){

    private lateinit var mView: View
    private lateinit var pagerAdapter: FragmentsAdapter
    private lateinit var pager: ScrollViewPager
    private lateinit var tabs: TabLayout

    private lateinit var tabString : List<String>

    protected open fun canScroll() : Boolean = true
    protected open fun offscreenPageLimit() : Int = 2
    protected open fun tabMode() = TabLayout.MODE_FIXED
    protected open fun setNotice(countList: List<Int>) {
        for (x in 0 until countList.size) {
            if (countList[x] != 0) setNoticeCount(x, countList[x])
        }
        var position = -1
        for (x in 0 until countList.size) {
            if (countList[x] != 0){
                position = x
                break
            }
        }
        if (position != -1) pager.currentItem = position
    }

    private fun setNoticeCount(position: Int, count: Int) {
        tabs.getTabAt(position)!!.customView!!.find<AppCompatTextView>(R.id.tab_notice).text = count.toString()
    }

    abstract fun getTabTitles() : List<String>
    abstract fun setFragments(adapter: FragmentsAdapter)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = layoutInflater.inflate(R.layout.fragment_base_nav, container, false)
        initPager()
        initTabLayout()
        pager.setupWithTabLayout(tabs)
        return mView
    }


    private fun initPager() {
        pager = mView.find(R.id.base_nav_pager)

        pagerAdapter = FragmentsAdapter(childFragmentManager)
        pager.offscreenPageLimit = offscreenPageLimit()
        pager.adapter = pagerAdapter
        pager.canScroll = canScroll()

        loadData()
    }

    private fun initTabLayout() {
        tabs = mView.find(R.id.base_nav_tab)

        tabString = getTabTitles()
        tabs.tabMode = tabMode()

        for (x in 0 until tabString.size) {
            val v = LayoutInflater.from(App.context).inflate(R.layout.layout_tab_notice, null, false)
            v.find<AppCompatTextView>(R.id.tab_title).text = tabString[x]
            val tab = tabs.newTab()
            tab.customView = v
            tabs.addTab(tab)
        }

    }

    override fun loadData() {
        setFragments(pagerAdapter)
    }

}
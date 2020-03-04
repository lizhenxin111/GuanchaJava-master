package com.lzx.guanchajava.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    lateinit var fragments : List<Fragment>
    var pageTitles = listOf<String>()
    var reGetTitle = false

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (reGetTitle) {
            var title = super.getPageTitle(position)
            if (pageTitles.isNotEmpty()) {
                title = pageTitles[position]
            }
            return title
        } else return super.getPageTitle(position)
    }   //重写该方法选择标题，防止在和TabLayout配合时没有标题

    override fun getCount(): Int = if (::fragments.isInitialized) fragments.size else 0
}
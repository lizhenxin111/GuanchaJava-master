package com.lzx.guanchajava.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.lzx.guanchajava.R
import org.jetbrains.anko.find

class ScrollViewPager : ViewPager {

    var canScroll = true

    private var mChildHeight = mutableListOf<Int>()
    private lateinit var mTabLayout: TabLayout


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context) : super(context)

    /*override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mChildHeight.size < x) {
            for (x in 0 until childCount) {
                val v = getChildAt(x)
                v.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
                mChildHeight.add(v.measuredHeight)
            }
        }

        val newHeightMeasureSpec = if (currentItem > mChildHeight.size || mChildHeight.isEmpty())
            heightMeasureSpec
        else
            MeasureSpec.makeMeasureSpec(mChildHeight[currentItem], MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }*/

    /**
     * 是否拦截
     * 拦截:会走到自己的onTouchEvent方法里面来
     * 不拦截:事件传递给子孩子
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // return false;//可行,不拦截事件,
        // return true;//不行,孩子无法处理事件
        //return super.onInterceptTouchEvent(ev);//不行,会有细微移动
        return if (canScroll) {
            super.onInterceptTouchEvent(ev)
        } else {
            false
        }
    }

    /**
     * 是否消费事件
     * 消费:事件就结束
     * 不消费:往父控件传
     */
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        //return false;// 可行,不消费,传给父控件
        //return true;// 可行,消费,拦截事件
        //super.onTouchEvent(ev); //不行,
        //虽然onInterceptTouchEvent中拦截了,
        //但是如果viewpage里面子控件不是viewgroup,还是会调用这个方法.
        return if (canScroll) {
            super.onTouchEvent(ev)
        } else {
            true// 可行,消费,拦截事件
        }
    }

    fun setupWithTabLayout(tabLayout: TabLayout) {
        mTabLayout = tabLayout
        tabLayout.addOnTabSelectedListener(onTabSelectedListener)
        addOnPageChangeListener(onPageChangeListener)
    }


    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            this@ScrollViewPager.currentItem = tab!!.position
            tab.customView!!.find<AppCompatTextView>(R.id.tab_notice).text = ""
        }

    }

    private val onPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            if (::mTabLayout.isInitialized) mTabLayout.setScrollPosition(position, 0f, false)
        }

    }
}
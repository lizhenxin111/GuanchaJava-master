package com.lzx.guanchajava.view.fragment.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.widget.EmptyRecyclerView
import org.jetbrains.anko.find

abstract class BaseListFragment<T : RecyclerView.Adapter<*>> : LazyFragment() {

    protected var mPageNo = 2   //加载第一页时不使用该标志
    protected var hasMore = true    //是否还有更多数据
    protected lateinit var mApi: String

    protected var mLoadNow : Boolean? = false        //直接加载，屏蔽懒加载

    protected lateinit var mAdapter: T

    protected lateinit var mSwipe : SwipeRefreshLayout
    protected lateinit var mList: EmptyRecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mApi = arguments?.getString(TAG.TAG_FRAGMENT_NEWS_LIST_URL).toString()
        mLoadNow = arguments?.getBoolean(TAG.TAG_FRAGMENT_NEWS_LIST_LOADNOW, false)

        val v = layoutInflater.inflate(R.layout.fragment_base_list, container, false)
        mList = v.findViewById(R.id.fragment_base_list)
        mSwipe = v.find(R.id.fragment_base_swipe)

        mList.layoutManager = getLayoutManager(context!!)
        mAdapter = getAdapter()
        mList.adapter = mAdapter
        mList.addOnScrollListener(onScrollObj)


        mSwipe.setColorSchemeColors(resources.getColor(R.color.colorPrimary))

        mSwipe.setOnRefreshListener {
            requestData(mAdapter, mSwipe)
            mPageNo = 2
        }

        if (mLoadNow == true) loadData()

        return v
    }

    private val onScrollObj = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if ((mList.computeVerticalScrollExtent() + mList.computeVerticalScrollOffset()) > mList.computeVerticalScrollRange() * 0.8
                    && newState == 0 && hasMore) {
                requestMore(mAdapter)
                hasMore = true
            }
        }
    }

    override fun loadData() {
        requestData(mAdapter, mSwipe)
    }

    protected open fun getLayoutManager(c: Context) : RecyclerView.LayoutManager{
        return LinearLayoutManager(c)
    }

    abstract fun getAdapter() : T

    abstract fun requestData(mAdapter: T, mSwipe: SwipeRefreshLayout)     //首次加载/刷新

    protected open fun requestMore(mAdapter: T) {        //加载更多

    }

}
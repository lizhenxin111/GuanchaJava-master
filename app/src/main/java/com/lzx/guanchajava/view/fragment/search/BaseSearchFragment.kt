package com.lzx.guanchajava.view.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.BaseRecyclerViewAdapter
import com.lzx.guanchajava.view.fragment.base.BaseFragment
import com.lzx.guanchajava.viewmodel.search.vmSearch
import com.lzx.guanchajava.view.widget.EmptyRecyclerView
import com.orhanobut.logger.Logger
import org.jetbrains.anko.find

/**
 * 以前基于BaseListFragment
 * 现在重新抽象
 * 主要改动:  泛型从 列表适配器的子类 改为 列表参数的子类
 */
abstract class BaseSearchFragment<T> : BaseFragment() {

    protected var mPageNo = 2   //加载第一页时不使用该标志
    protected var hasMore = true    //是否还有更多数据
    protected lateinit var mApi: String

    protected var mLoadNow : Boolean? = false        //直接加载，屏蔽懒加载

    protected lateinit var mAdapter: BaseRecyclerViewAdapter<T>
    protected lateinit var mSwipe : SwipeRefreshLayout
    protected lateinit var mList: EmptyRecyclerView

    protected val mSearchViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
                .get(vmSearch::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //TODO("不发起搜索请求")

        mApi = arguments?.getString(TAG.TAG_FRAGMENT_NEWS_LIST_URL).toString()
        mLoadNow = arguments?.getBoolean(TAG.TAG_FRAGMENT_NEWS_LIST_LOADNOW, false)

        val v = layoutInflater.inflate(R.layout.fragment_base_list, container, false)
        mList = v.findViewById(R.id.fragment_base_list)
        mSwipe = v.find(R.id.fragment_base_swipe)

        mAdapter = getAdapter()

        mList.apply {
            layoutManager = getLayoutManager()
            adapter = mAdapter
            addOnScrollListener(onScrollObj)
        }

        mSwipe.apply {
            setColorSchemeColors(resources.getColor(R.color.colorPrimary))
            setOnRefreshListener {
                requestData()
                mPageNo = 2
            }
        }

        if (mLoadNow == true) requestData()

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (mLoadNow == true) requestData()
    }

    private val onScrollObj = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if ((mList.computeVerticalScrollExtent() + mList.computeVerticalScrollOffset()) > mList.computeVerticalScrollRange() * 0.8
                    && newState == 0 && hasMore) {
                requestMore()
                hasMore = true
            }
        }
    }

    protected open fun getLayoutManager() : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())

    protected abstract fun getAdapter(): BaseRecyclerViewAdapter<T>

    protected open fun requestData() {
    }

    protected abstract fun requestMore()

    protected fun notifyItemInsert(data: List<T>) {
        val p1 = mAdapter.itemCount
        mAdapter.data.addAll(data)
        val p2 = mAdapter.itemCount
        mAdapter.notifyItemRangeInserted(p1, p2)
        mSwipe.isRefreshing = false
        Logger.d("$p1    $p2")
    }
}
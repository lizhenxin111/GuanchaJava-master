package com.lzx.guanchajava.view.fragment.bottomNavFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.author.Item
import com.lzx.guanchajava.POJO.bean.db.DBAuthor
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.AuthorDetailActivity
import com.lzx.guanchajava.adapter.authorAdapter.AuthorAdapter
import com.lzx.guanchajava.adapter.authorAdapter.AuthorHistoryAdapter
import com.lzx.guanchajava.adapter.authorAdapter.TypeAuthor
import com.lzx.guanchajava.adapter.authorAdapter.TypeLabel
import com.lzx.guanchajava.db.AuthorDB
import com.lzx.guanchajava.view.fragment.base.LazyFragment
import com.lzx.guanchajava.util.JumpUtil
import com.lzx.guanchajava.util.PopupUtils
import com.lzx.guanchajava.viewmodel.bottomNavigation.vmAuthor
import com.lzx.guanchajava.view.widget.EmptyRecyclerView

/**
 * BottomNavigationView中的专栏
 */
class AuthorFragment : LazyFragment() {

    private val mAuthorViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
                .get(vmAuthor::class.java)
    }

    private val historyAdapter = AuthorHistoryAdapter()
    private val allAdapter = AuthorAdapter()

    /**
     * LazyFragment的加载数据
     */
    override fun loadData() {
        loadAll()
        loadHistory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_author, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initList()

        mAuthorViewModel.allAuthors.observe(viewLifecycleOwner, Observer {
            allAdapter.data = it
            allAdapter.notifyDataSetChanged()
        })

        mAuthorViewModel.historyAuthor.observe(viewLifecycleOwner, Observer {
            historyAdapter.data = (it as MutableList<DBAuthor>).asReversed()
            historyAdapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        loadHistory()
    }

    /**
     * 初始化两个list
     */
    private fun initList() {
        //历史
        historyAdapter.apply {
            onClickListener = historyClickObj
            onLongClickListener = historyLongClickObj
        }
        view!!.findViewById<EmptyRecyclerView>(R.id.author_list_history).apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = historyAdapter
        }


        //所有
        allAdapter.apply {
            onClickListener = allClickObj
            types = listOf(TypeAuthor(), TypeLabel())
        }
        view!!.findViewById<EmptyRecyclerView>(R.id.author_list_all).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allAdapter
        }
    }





    /**
     * 加载作者点击记录
     */
    private fun loadHistory() {
        mAuthorViewModel.loadHistoryAuthors()
    }

    /**
     * 加载所有记着
     */
    private fun loadAll() {
        mAuthorViewModel.loadAllAuthors(Api.zlzj())
    }


    /**
     * 一堆点击时间
     */
    private val historyClickObj = object : AuthorHistoryAdapter.OnClickListener {
        override fun onClick(item: DBAuthor) {
            goToDetail(item.author_id)
        }
    }

    private val historyLongClickObj = object : AuthorHistoryAdapter.OnLongClickListener {
        override fun onLongClick(view: View, item: DBAuthor) {
            PopupUtils.popupDelete(view) {
                AuthorDB.deleteAuthor(item.author_id)
                loadHistory()
            }
        }
    }

    private val allClickObj = object : AuthorAdapter.OnClickListener {
        override fun onClick(item: Item) {
            goToDetail(item.id)
            DBAuthor(item.id, item.name, item.pic, item.cate, item.summary, item.article_count.toInt()).save()
        }
    }


    /**
     * 跳转到作者详情页面
     */
    private fun goToDetail(id: String) {
        JumpUtil.startActivity(requireActivity(), AuthorDetailActivity::class.java, "ID" to id)
    }
}
package com.lzx.guanchajava.member.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.view.activity.NewsActivity
import com.lzx.guanchajava.view.activity.PostActivity
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.member.adapter.ArticleListAdapter
import com.lzx.guanchajava.member.bean.Article
import com.lzx.guanchajava.member.db.NoteDB
import com.lzx.guanchajava.util.JumpUtil

class ArticleListFragment : BaseListFragment<ArticleListAdapter>() {

    companion object {
        fun newInstance() = ArticleListFragment()
        fun newInstance(loadNow : Boolean) = ArticleListFragment().apply {
            arguments = Bundle().apply {
                putBoolean(TAG.TAG_FRAGMENT_NEWS_LIST_LOADNOW, loadNow)
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }
    override fun getAdapter() = ArticleListAdapter(onItemClickListener)

    override fun requestData(mAdapter: ArticleListAdapter, mSwipe: SwipeRefreshLayout) {
        mAdapter.data.clear()
        mAdapter.data.addAll(NoteDB.queryAllArticle())
        mAdapter.notifyDataSetChanged()
        mSwipe.isRefreshing = false
    }

    private val onItemClickListener = object : ArticleListAdapter.OnItemClickListener {
        override fun onTitleClick(note: Article) {
            if (note.article_type == "1") JumpUtil.startActivity(this@ArticleListFragment.activity!!, NewsActivity::class.java, "ID" to note.article_id, "CODETYPE" to note.article_type)
            else JumpUtil.startActivity(this@ArticleListFragment.activity!!, PostActivity::class.java, "ID" to note.article_id, "CODETYPE" to note.article_type)
        }

        override fun onButtonClick(note: Article) {
            NoteListFragment.newInstance(note.article_id).show(childFragmentManager, "NOTE")
        }

    }
}
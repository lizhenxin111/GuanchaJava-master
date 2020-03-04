package com.lzx.guanchajava.member.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.view.activity.NewsActivity
import com.lzx.guanchajava.view.activity.PostActivity
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.member.adapter.CommentCollectionAdapter
import com.lzx.guanchajava.member.bean.DBComment
import com.lzx.guanchajava.member.db.CCDB
import com.lzx.guanchajava.util.PopupUtils
import com.orhanobut.logger.Logger

/**
 * 展示收藏评论的fragment
 * @property onClickListener <no name provided>
 * @property onLongClickListener <no name provided>
 */
class CommentCollectedFragment : BaseListFragment<CommentCollectionAdapter>() {

    companion object {
        fun newInstance() = CommentCollectedFragment()
        fun newInstance(loadNow : Boolean) = CommentCollectedFragment().apply {
            arguments = Bundle().apply {
                putBoolean(TAG.TAG_FRAGMENT_NEWS_LIST_LOADNOW, loadNow)
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun getAdapter(): CommentCollectionAdapter
            = CommentCollectionAdapter(fragmentManager!!, onClickListener, onLongClickListener)

    override fun requestData(mAdapter: CommentCollectionAdapter, mSwipe: SwipeRefreshLayout) {
        mAdapter.data.clear()
        val list = CCDB.queryComments()
        Logger.d(list.size)
        mAdapter.data.addAll(list)
        mAdapter.notifyDataSetChanged()
        mSwipe.isRefreshing = false
    }

    private val onClickListener = object : CommentCollectionAdapter.OnClickListener {
        override fun onClick(id: Int, type: Int) {
            if (type == 1)
                goto(NewsActivity::class.java, "ID" to id.toString(), "CODETYPE" to type.toString())
            else if (type == 2)
                goto(PostActivity::class.java, "ID" to id.toString(), "CODETYPE" to type.toString())
        }
    }

    private val onLongClickListener = object  : CommentCollectionAdapter.OnLongClickListener {
        override fun onLongClick(view: View, comment: DBComment, position: Int) {
            PopupUtils.popupDelete(view) {
                CCDB.deleteComment(comment)
                mAdapter.data.removeAt(position)
                mAdapter.notifyItemRemoved(position)
            }
        }
    }
}
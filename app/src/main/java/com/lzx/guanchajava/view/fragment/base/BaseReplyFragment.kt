package com.lzx.guanchajava.view.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.replyAdapter.ReplyAdapter
import com.lzx.guanchajava.adapter.replyAdapter.TypeLabel
import com.lzx.guanchajava.adapter.replyAdapter.TypeReply
import com.lzx.guanchajava.member.db.HardBlackDB
import com.lzx.guanchajava.util.ResourceUtil
import com.lzx.guanchajava.view.widget.EmptyRecyclerView
import com.lzx.guanchajava.view.widget.FixBottomSheetDialogFragment
import org.jetbrains.anko.find


abstract class BaseReplyFragment : FixBottomSheetDialogFragment() {

    protected var pageNo = 2
    protected var hasMore = true

    protected lateinit var mArticleId: String
    protected lateinit var mArticleType: String
    protected lateinit var mTitle: String
    private val mLayoutManager = LinearLayoutManager(context)

    protected lateinit var mList: EmptyRecyclerView
    protected val mAdapter : ReplyAdapter by lazy { ReplyAdapter(arguments!!.getString("TITLE")) }

    /**
     * 子类只能在onCreateView之后操作该控件
     */
    protected val mCommentButton by lazy {
        view?.findViewById<FloatingActionButton>(R.id.reply_comment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_reply, container, false)
        mTitle = arguments!!.getString("TITLE")
        val toolbar = v.find<androidx.appcompat.widget.Toolbar>(R.id.reply_toolbar)
        mList = v.findViewById(R.id.reply_list)

        toolbar.title = "评论"
        toolbar.setTitleTextColor(ResourceUtil.getColor(R.color.colorTextInDark))
        return v
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mArticleId = arguments!!.getString("ID", "")
        mArticleType = arguments!!.getString("TYPE", "")

        mAdapter.types = listOf(TypeReply(context!!, fragmentManager!!, mTitle, HardBlackDB.queryAllUid()), TypeLabel(mTitle))

        mList.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = mLayoutManager
            addOnScrollListener(onScrollObj)
            adapter = mAdapter
        }
        requestData()
    }


    private val onScrollObj = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if ((mList.computeVerticalScrollExtent() + mList.computeVerticalScrollOffset()) > mList.computeVerticalScrollRange() * 0.8
                    && newState == 0 && hasMore)
                requestMore()
        }
    }

    abstract fun requestData()

    abstract fun requestMore()         //加载更多


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) dismiss()
        return super.onOptionsItemSelected(item)
    }
}

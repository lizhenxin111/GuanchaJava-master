package com.lzx.guanchajava.view.fragment.reply


import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.view.fragment.base.BaseReplyFragment
import com.lzx.guanchajava.viewmodel.reply.vmReply
import com.orhanobut.logger.Logger
import org.jetbrains.anko.sdk27.coroutines.onClick

class ReplyFragment : BaseReplyFragment(){

    private val mReplyViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
                .get(vmReply::class.java)
    }

    companion object {
        fun newInstance(id: String, type: String, title: String) : ReplyFragment = ReplyFragment().apply {
            arguments = Bundle().apply {
                putString("ID", id)
                putString("TYPE", type)
                putString("TITLE", title)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCommentButton?.apply {
            visibility = FloatingActionButton.VISIBLE
            onClick {
                CommentFragment.newInstance(mArticleType, mArticleId, "0",  "111")
                        .show(fragmentManager!!, "COMMENT")
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mReplyViewModel.reply.observe(viewLifecycleOwner, Observer {
            val p1 = mAdapter.itemCount
            mAdapter.data.addAll(it)
            val p2 = mAdapter.itemCount
            Logger.d("$p1     $p2")
            mAdapter.notifyItemRangeInserted(p1, p2)
        })
    }


    override fun requestData() {
        mReplyViewModel.getReply(Api.getComment(mArticleId, mArticleType) + 1)
    }

    override fun requestMore() {
        mReplyViewModel.getReply(Api.getComment(mArticleId, mArticleType) + pageNo++)
    }
}

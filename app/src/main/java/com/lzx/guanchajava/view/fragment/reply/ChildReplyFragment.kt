package com.lzx.guanchajava.view.fragment.reply

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.view.fragment.base.BaseReplyFragment
import com.lzx.guanchajava.viewmodel.reply.vmReply

class ChildReplyFragment : BaseReplyFragment() {

    private val mReplyViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
                .get(vmReply::class.java)
    }

    companion object {
        fun newInstance(id: String, title: String) : ChildReplyFragment = ChildReplyFragment().apply {
            arguments = Bundle().apply {
                putString("ID", id)
                putString("TITLE", title)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCommentButton?.visibility = View.GONE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mReplyViewModel.childReply.observe(viewLifecycleOwner, Observer {
            mAdapter.data = it
            mAdapter.notifyDataSetChanged()
        })
    }


    override fun requestData() {
        mReplyViewModel.getChildReply(Api.getChildComment(mArticleId))
    }

    override fun requestMore() {}
}
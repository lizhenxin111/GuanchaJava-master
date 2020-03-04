package com.lzx.guanchajava.view.fragment.userDetail

import android.os.Bundle
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.adapter.FragmentsAdapter
import com.lzx.guanchajava.view.fragment.base.BaseNavFragment
import com.lzx.guanchajava.util.LoginUtil

class UserPublishFragment : BaseNavFragment() {
    companion object {
        fun newInstance() = UserPublishFragment()
        fun newInstance(replyCount: Int, publishCount: Int, praiseCount: Int) = UserPublishFragment().apply {
            arguments = Bundle().apply {
                putInt("REPLY", replyCount)
                putInt("PUBLISH", publishCount)
                putInt("PRAISE", praiseCount)
            }
        }
    }

    override fun getTabTitles(): List<String> = listOf("回复", "文章", "点赞")

    override fun setFragments(adapter: FragmentsAdapter) {
        adapter.fragments = listOf(UserReplyFragment.newInstance(Api.userComment(LoginUtil.getUid().toString())),
                UserArticleFragment.newInstance(Api.userPublish(LoginUtil.getUid().toString())),
                UserPraiseFragment.newInstance(Api.userPraise(LoginUtil.getUid().toString()))
        )
        adapter.notifyDataSetChanged()
    }

}
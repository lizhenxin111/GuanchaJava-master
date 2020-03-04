package com.lzx.guanchajava.view.fragment.userInfo.history

import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.adapter.FragmentsAdapter
import com.lzx.guanchajava.view.fragment.base.BaseNavFragment
import com.lzx.guanchajava.view.fragment.userDetail.UserPraiseFragment
import com.lzx.guanchajava.util.LoginUtil

class UserHistoryFragment : BaseNavFragment() {
    override fun getTabTitles(): List<String> = listOf("浏览历史", "点赞历史")

    override fun setFragments(adapter: FragmentsAdapter) {
        adapter.fragments = listOf(
                NewsHistoryFragment(),
                UserPraiseFragment.newInstance(Api.userPraise(LoginUtil.getUid().toString())))
        adapter.notifyDataSetChanged()
    }
}
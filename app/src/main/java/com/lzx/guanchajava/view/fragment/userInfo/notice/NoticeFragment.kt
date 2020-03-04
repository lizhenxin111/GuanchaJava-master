package com.lzx.guanchajava.view.fragment.userInfo.notice

import android.os.Bundle
import android.view.View
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.notice.NoticeCount
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.FragmentsAdapter
import com.lzx.guanchajava.view.fragment.base.BaseNavFragment
import com.lzx.guanchajava.util.LoginUtil
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.support.v4.longToast

class NoticeFragment : BaseNavFragment() {

    override fun getTabTitles(): List<String> = listOf("被回复", "被点赞", "被投票", "被关注")

    override fun setFragments(adapter: FragmentsAdapter) {
        adapter.fragments = listOf(UserRepliedFragment.newInstance(Api.replied(LoginUtil.getUid())),
                UserPraisedFragment.newInstance(Api.praised(LoginUtil.getUid())),
                UserVotedFragment.newInstance(Api.userVoted()),
                FollowedFragment.newInstance(Api.followed()))
        adapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Fuel.get(Api.noticeCount()).responseString { _, response, result ->
            val(data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val data = Gson().fromJson(result.get(), NoticeCount::class.java).data
                setNotice(listOf(data.reply_count, data.collection_praise_count, data.vote_count, data.attention_count))
            }
        }
    }

    override fun offscreenPageLimit(): Int {
        return 4
    }
}
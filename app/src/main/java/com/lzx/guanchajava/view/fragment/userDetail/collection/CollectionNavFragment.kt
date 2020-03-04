package com.lzx.guanchajava.view.fragment.userDetail.collection

import android.os.Bundle
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.adapter.FragmentsAdapter
import com.lzx.guanchajava.view.fragment.base.BaseNavFragment

class CollectionNavFragment : BaseNavFragment() {

    companion object {
        fun newInstance(uid: String) = CollectionNavFragment().apply {
            arguments = Bundle().apply {
                putString("UID", uid)
            }
        }
    }

    override fun getTabTitles(): List<String> = listOf("新闻")

    override fun setFragments(adapter: FragmentsAdapter) {
        adapter.fragments = listOf(
                UserCollectionFragment.newInstance(Api.userCollect(arguments!!.getString("UID")), true)
                /*CommentCollectedFragment()*/
        )
        adapter.notifyDataSetChanged()
    }
}
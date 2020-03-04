package com.lzx.guanchajava.view.fragment.ItemListFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.adapter.FragmentsAdapter
import com.lzx.guanchajava.view.fragment.base.BaseNavFragment

class PostTopicNavFragment : BaseNavFragment() {

    private lateinit var ID : String

    companion object {
        fun newInstance(id: String) : PostTopicNavFragment = PostTopicNavFragment().apply {
            arguments = Bundle().apply {
                putString("ID", id)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ID = arguments!!.getString("ID")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getTabTitles(): List<String> = listOf("最新发布", "最新回复")

    override fun setFragments(adapter: FragmentsAdapter) {
        adapter.fragments = listOf(PostTopicListFragment.newInstance(Api.postTopicPublish(ID)),
                PostTopicListFragment.newInstance(Api.postTopicReply(ID)))
        adapter.notifyDataSetChanged()
    }

}
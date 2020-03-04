package com.lzx.guanchajava.view.fragment.search

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.bean.search.postTopic.Item
import com.lzx.guanchajava.view.activity.PostTopicActivity
import com.lzx.guanchajava.adapter.search.SearchPostTopicAdapter
import org.jetbrains.anko.support.v4.startActivity

class SearchPostTopicFragment : BaseSearchFragment<Item>() {

    companion object {
        fun newInstance(url: String): SearchPostTopicFragment = SearchPostTopicFragment().apply {
            arguments = Bundle().apply {
                putString(TAG.TAG_FRAGMENT_NEWS_LIST_URL, url)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSearchViewModel.searchPostTopic.observe(viewLifecycleOwner, Observer {
            notifyItemInsert(it)
        })
    }

    override fun getAdapter(): SearchPostTopicAdapter = SearchPostTopicAdapter(object : SearchPostTopicAdapter.OnClickListener {
        override fun onClick(item: Item) {
            startActivity<PostTopicActivity>(TAG.POST_TOPIC_ID to item.topic_id,
                    TAG.POST_TOPIC_NAME to item.topic_name,
                    TAG.POST_TOPIC_ARTICLE to item.post_nums.toString(),
                    TAG.POST_TOPIC_SUBSCRIBE to item.attention_nums,
                    TAG.POST_TOPIC_HAS_ATTENTION to item.has_attention,
                    TAG.POST_TOPIC_PIC to item.topic_img)
        }
    })

    override fun requestData() {
        mSearchViewModel.searchPostTopic(mApi + 1)
    }

    override fun requestMore() {
        mSearchViewModel.searchPostTopic(mApi + mPageNo++)
    }
}
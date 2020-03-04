package com.lzx.guanchajava.view.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lzx.guanchajava.POJO.bean.topic.Data
import com.lzx.guanchajava.viewmodel.content.vmContent

/**
 * 风闻
 */
class PostActivity : BaseWebActivity() {
    override fun getContent() {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
                .get(vmContent::class.java)
                .apply {
                    getTopicContent(mArticleId)
                    topicContent.observe(this@PostActivity, Observer {
                        handleData(it.data[0])
                    })
                }
    }
    
    private fun handleData(topic: Data) {
        setArticleInfo(topic.title, topic.summary, topic.topics, topic.id.toString())
        setContent(topic.content)
        setAuthorInfo(topic.user_id.toString(), topic.user_nick, topic.user_photo, topic.created_at)
        setNumbers(topic.comment_count, topic.has_collection, topic.has_praise)
        if (topic.has_vote) {
            setVote(topic.vote_info[0])
        }
    }
}

package com.lzx.guanchajava.view.activity

import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.fragment.ItemListFragment.PostTopicNavFragment
import kotlinx.android.synthetic.main.activity_post_topic.*

class PostTopicActivity : BaseActivity() {

    private lateinit var mName: String
    private lateinit var mArticleCount: String
    private lateinit var mAttentionCount: String
    private lateinit var mTopicId: String
    private var mHasAttention: Boolean = false
    private lateinit var mPic: String

    override fun loadUI() {
        setContentView(R.layout.activity_post_topic)
    }

    override fun loadContent() {
        initBaseInfo()
        initArticles()
    }

    override fun loadOther() {
        initProperty()
    }

    private fun initProperty() {
        mName = intent.getStringExtra(TAG.POST_TOPIC_NAME)
        mArticleCount = intent.getStringExtra(TAG.POST_TOPIC_ARTICLE)
        mAttentionCount = intent.getStringExtra(TAG.POST_TOPIC_SUBSCRIBE)
        mTopicId = intent.getStringExtra(TAG.POST_TOPIC_ID)
        mPic = intent.getStringExtra(TAG.POST_TOPIC_PIC)
        mHasAttention = intent.getBooleanExtra(TAG.POST_TOPIC_HAS_ATTENTION, false)

    }

    private fun initBaseInfo() {
        post_pic.url = mPic
        post_name.text = mName
        post_des.text = "文章：$mArticleCount    关注：$mAttentionCount"
    }

    private fun initArticles() {
        supportFragmentManager.beginTransaction().replace(R.id.post_container, PostTopicNavFragment.newInstance(mTopicId)).commit()
    }
}

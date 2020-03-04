package com.lzx.guanchajava.view.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lzx.guanchajava.POJO.bean.news.News
import com.lzx.guanchajava.viewmodel.content.vmContent
import org.jetbrains.anko.longToast


/**
 * 新闻
 */
class NewsActivity : BaseWebActivity() {

    override fun getContent() {
        var type = 1.toString()
        if (mCodeType == 2.toString()) {
            type = 2.toString()
        }
        mCodeType = 1.toString()
        
       ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
                .get(vmContent::class.java)
                .apply {
                    getNewsContent(mArticleId, type)
                    newsContent.observe(this@NewsActivity, Observer {
                        handleData(it)
                    })
                }
    }

    private fun handleData(news: News) {
        if (news.code == 0) {
            ifInsertNews = true
            val data = news.data
            setArticleInfo(data.title, data.daodu, listOf(), data.id)
            setContent(data.content)
            setEditorInfo(data.source, data.editor.name, data.news_time)
            if (data.author != null && data.author.isNotEmpty()) {
                val author = data.author[0]
                setAuthorInfo(author.id, author.name, author.pic, data.news_time)
            }
            setNumbers(data.comment_num,  data.has_collection, false)
        } else {
            ifInsertNews = false
            longToast(news.msg)
            finish()
        }
    }
}

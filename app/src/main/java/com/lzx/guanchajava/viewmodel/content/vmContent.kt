package com.lzx.guanchajava.viewmodel.content

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.news.News
import com.lzx.guanchajava.POJO.bean.topic.Topic

/**
 * 新闻和风闻的具体内容
 */
class vmContent : ViewModel() {

    private val _newsContent = MutableLiveData<News>()
    val newsContent : MutableLiveData<News>
        get() = _newsContent

    private val _topicContent = MutableLiveData<Topic>()
    val topicContent : MutableLiveData<Topic>
        get() = _topicContent


    fun getNewsContent(mArticleId: String, type: String) {
        Fuel.get(Api.xwnr(mArticleId, type)).responseString { request, response, result ->
            val rs = result.get().replace("[]", "{}")
            val r = Gson().fromJson(rs, News::class.java)
            _newsContent.value = r
        }
    }

    fun getTopicContent(mArticleId: String) {
        Fuel.get(Api.fwnr(mArticleId)).responseString { request, response, result ->
            val data = Gson().fromJson(result.get(), Topic::class.java)
            _topicContent.value = data
        }
    }
}
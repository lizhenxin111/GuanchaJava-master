package com.lzx.guanchajava.viewmodel.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.bean.search.author.Item
import com.lzx.guanchajava.POJO.bean.search.author.SearchAuthor
import com.lzx.guanchajava.POJO.bean.search.news.SearchNews
import com.lzx.guanchajava.POJO.bean.search.newsTopic.NewsTopic
import com.lzx.guanchajava.POJO.bean.search.postTopic.PostTopic
import com.lzx.guanchajava.POJO.bean.search.user.SearchUser
import com.lzx.guanchajava.POJO.bean.topicList.TopicItem
import com.lzx.guanchajava.POJO.bean.topicList.TopicList
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.longToast

class vmSearch(application: Application) : AndroidViewModel(application) {


    private val _searchAuthor = MutableLiveData<List<Item>>()
    val searchAuthor : MutableLiveData<List<Item>>
        get() = _searchAuthor

    private val _searchNews = MutableLiveData<List<com.lzx.guanchajava.POJO.bean.search.news.Item>>()
    val searchNews : MutableLiveData<List<com.lzx.guanchajava.POJO.bean.search.news.Item>>
        get() = _searchNews

    private val _searchNewsTopic = MutableLiveData<List<com.lzx.guanchajava.POJO.bean.search.newsTopic.Item>>()
    val searchNewsTopic : MutableLiveData<List<com.lzx.guanchajava.POJO.bean.search.newsTopic.Item>>
        get() = _searchNewsTopic

    private val _searchPost = MutableLiveData<List<TopicItem>>()
    val searchPost : MutableLiveData<List<TopicItem>>
        get() = _searchPost

    private val _searchPostTopic = MutableLiveData<List<com.lzx.guanchajava.POJO.bean.search.postTopic.Item>>()
    val searchPostTopic : MutableLiveData<List<com.lzx.guanchajava.POJO.bean.search.postTopic.Item>>
        get() = _searchPostTopic

    private val _searchUser = MutableLiveData<List<com.lzx.guanchajava.POJO.bean.search.user.Item>>()
    val searchUser : MutableLiveData<List<com.lzx.guanchajava.POJO.bean.search.user.Item>>
        get() = _searchUser






    fun searchAuthor(url: String) {
        Fuel.get(url).responseObject(SearchAuthor.Deserializer()) { request, response, result ->
            val(data, error) = result
            if (error != null) {
                getApplication<Application>().longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _searchAuthor.value = result.get().data.items as MutableList<Item>
            }
        }
    }

    fun searchNews(url: String) {
        Fuel.get(url).responseObject(SearchNews.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                App.context.longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _searchNews.value = result.get().data.items
            }
        }
    }

    fun searchNewsTopic(url: String) {
        Fuel.get(url).responseObject(NewsTopic.Deserializer()) { request, response, result ->
            val(data, error) = result
            if (error != null) {
                App.context.longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _searchNewsTopic.value = result.get().data.items
            }
        }
    }

    fun searchPost(url: String) {
        Fuel.get(url).responseObject(TopicList.Deserializer()) { _, response, result ->
            val (data, error) = result
            if (error != null) {
                getApplication<Application>().longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _searchPost.value = result.get().data.items
            }
        }
    }

    fun searchPostTopic(url: String) {
        Fuel.get(url).responseObject(PostTopic.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                App.context.longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _searchPostTopic.value = result.get().data.items
            }
        }
    }

    fun searchUser(url: String) {
        Fuel.get(url).responseObject(SearchUser.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                getApplication<Application>().longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _searchUser.value = result.get().data.items
            }
        }
    }
}
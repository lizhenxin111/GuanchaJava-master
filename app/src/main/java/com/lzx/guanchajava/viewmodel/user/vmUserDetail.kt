package com.lzx.guanchajava.viewmodel.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.bean.userDetail.article.UserArticle
import com.lzx.guanchajava.POJO.bean.userDetail.collection.Item
import com.lzx.guanchajava.POJO.bean.userDetail.collection.UserCollection
import com.lzx.guanchajava.POJO.bean.userDetail.reply.Comment
import com.lzx.guanchajava.POJO.bean.userDetail.reply.UserReply
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.longToast

class vmUserDetail(application: Application) : AndroidViewModel(application) {

    private val _userCollection = MutableLiveData<List<Item>>()
    val userCollection : MutableLiveData<List<Item>>
        get() = _userCollection

    private val _userArticle = MutableLiveData<List<com.lzx.guanchajava.POJO.bean.userDetail.article.Item>>()
    val userArticle : MutableLiveData<List<com.lzx.guanchajava.POJO.bean.userDetail.article.Item>>
        get() = _userArticle

    private val _userPraise = MutableLiveData<List<com.lzx.guanchajava.POJO.bean.userDetail.article.Item>>()
    val userPraise : MutableLiveData<List<com.lzx.guanchajava.POJO.bean.userDetail.article.Item>>
        get() = _userPraise

    private val _userReply = MutableLiveData<List<Comment>>()
    val userReply : MutableLiveData<List<Comment>>
        get() = _userReply





    fun getUserCollection(url: String) {
        Fuel.get(url).responseObject(UserCollection.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                getApplication<Application>().longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _userCollection.value = result.get().data.items
            }
        }
    }

    fun getUserArticle(url: String) {
        Fuel.get(url).responseObject(UserArticle.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                getApplication<Application>().longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _userArticle.value = result.get().data.items
            }
        }
    }

    fun getUserPraise(url: String) {
        Fuel.get(url).responseObject(UserArticle.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                getApplication<Application>().longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _userPraise.value = result.get().data.items
            }
        }
    }

    fun getUserReply(url: String) {
        Fuel.get(url).responseObject(UserReply.Deserializer()) { request, response, result ->
            val(data, error) = result
            if (error != null) {
                getApplication<Application>().longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _userReply.value = result.get().data.comments
            }
        }
    }
}
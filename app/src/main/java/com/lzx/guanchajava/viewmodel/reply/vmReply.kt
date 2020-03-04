package com.lzx.guanchajava.viewmodel.reply

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.bean.operate.publishComment.PublishComment
import com.lzx.guanchajava.POJO.bean.reply.BaseReply
import com.lzx.guanchajava.POJO.bean.reply.ReplyList
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.ResourceUtil
import com.orhanobut.logger.Logger
import org.jetbrains.anko.longToast

class vmReply(application: Application) : AndroidViewModel(application) {

    private val _reply = MutableLiveData<MutableList<BaseReply>>()
    val reply : MutableLiveData<MutableList<BaseReply>>
        get() = _reply


    private val _childReply = MutableLiveData<MutableList<BaseReply>>()
    val childReply : MutableLiveData<MutableList<BaseReply>>
        get() = _childReply

    private val _postCommentResult = MutableLiveData<PublishComment>()
    val postCommentResult : MutableLiveData<PublishComment>
        get() = _postCommentResult


    fun getReply(url: String) {
        Fuel.get(url).responseString { request, response, result ->
            val resultList = mutableListOf<BaseReply>()     //评论列表
            val data = Gson().fromJson(result.get(), ReplyList::class.java).data
            if (data.hot_count != 0) {
                val hot = BaseReply(20, "热门评论：${data.hot_count}")
                resultList.add(hot)
                resultList.addAll(data.hots)
            }
            val all = BaseReply(20, "全部评论：${data.count}")
            resultList.add(all)
            resultList.addAll(data.items)
            Logger.d(resultList)
            _reply.value = resultList
        }
    }

    fun getChildReply(url: String) {

        Fuel.get(url).responseString { request, response, result ->
            val resultList = mutableListOf<BaseReply>()
            val data = Gson().fromJson(result.get(), ReplyList::class.java).data

            val d = data.it.apply {
                showMore = false
            }
            resultList.add(d)
            val all = BaseReply(20, "回复：${data.items.size}条")
            resultList.add(all)
            resultList.addAll(data.items)

            _childReply.value = resultList
        }

    }


    fun postComment(path: String, parentId: String, type: String, codeId: String, content: String) {
        Fuel.post(path, listOf("parant_id" to parentId,
                "from" to "cms",
                "type" to type,
                "access_device" to "3",
                "code_id" to codeId,
                "content" to content)).responseString { request, response, result ->
            val (data, error) = result
            if (error != null) {
                getApplication<Application>().longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _postCommentResult.value = Gson().fromJson(result.get(), PublishComment::class.java)
            }
        }
    }
}
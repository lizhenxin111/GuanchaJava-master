package com.lzx.guanchajava.viewmodel.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.bean.postTopicList.Data
import com.lzx.guanchajava.POJO.bean.postTopicList.PostTopicList
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.longToast


/**
 * PostTopicListFragment
 */
class vmPostTopicList(application: Application) : AndroidViewModel(application) {

    private var _topicList = MutableLiveData<MutableList<Data>>()
    val topicList : MutableLiveData<MutableList<Data>>
        get() = _topicList

    fun getTopicList(url: String) {
        Fuel.get(url).responseObject(PostTopicList.Deserializer()) { _, response, result ->
            val(data, error) = result
            if (error != null) {
                App.context.longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _topicList.value = result.get().data
            }
        }
    }
}
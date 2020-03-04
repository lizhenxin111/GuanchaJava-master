package com.lzx.guanchajava.viewmodel.bottomNavigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.bean.topicList.TopicList
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.longToast

/**
 * bottomNavigation的风闻
 */
class vmTopicList(application : Application) : AndroidViewModel(application) {

    private var _topicListData = MutableLiveData<TopicList>()
    val topicListData : MutableLiveData<TopicList>
        get() = _topicListData


    fun loadData(url: String) {
        Fuel.get(url).responseObject(TopicList.Deserializer()) { _, _, result ->
            val(data, error) = result
            if (error != null) {
                getApplication<Application>().longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _topicListData.value = result.get()
            }
        }
    }
}
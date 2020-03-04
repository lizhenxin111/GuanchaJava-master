package com.lzx.guanchajava.viewmodel.bottomNavigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.bean.newsListWithBanner.TopicList
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.longToast

/**
 * bottomNavigation的新闻
 */
class vmNewsListBanner(application: Application) : AndroidViewModel(application) {

    private var _newsListData = MutableLiveData<TopicList>()
    val newsListData : MutableLiveData<TopicList>
        get() = _newsListData


    fun loadData(url: String) {

        Fuel.get(url).responseObject(TopicList.Deserializer()) { _, response, result ->
            val(data, error) = result
            if (error != null) {
                getApplication<Application>().longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _newsListData.value = result.get()
            }
        }
    }
}
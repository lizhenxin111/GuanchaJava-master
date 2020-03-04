package com.lzx.guanchajava.viewmodel.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.bean.newsList.News
import com.lzx.guanchajava.POJO.bean.newsList.NewsList
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.longToast

/**
 * NewsListFragment
 */
class vmNewsList(application: Application) : AndroidViewModel(application) {

    private var _newsList = MutableLiveData<List<News>>()
    val newsList : MutableLiveData<List<News>>
        get() = _newsList

    fun getNewsList(url: String) {
        Fuel.get(url).responseObject(NewsList.Deserializer()) { _, response, result ->
            val(data, error) = result
            if (error != null) {

                getApplication<Application>().longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                _newsList.value = result.get().data.items
            }
        }
    }
}
package com.lzx.guanchajava.viewmodel.bottomNavigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.bean.author.AuthorList
import com.lzx.guanchajava.POJO.bean.author.Item
import com.lzx.guanchajava.POJO.bean.db.DBAuthor
import com.lzx.guanchajava.db.AuthorDB
import org.jetbrains.anko.doAsync

/**
 * bottomNavigation的作者
 */
class vmAuthor(application: Application) : AndroidViewModel(application) {

    //所有作者, 从网络获取数据
    private var _allAuthor = MutableLiveData<List<Item>>()
    val allAuthors : MutableLiveData<List<Item>>
        get() = _allAuthor

    private var _historyAuthor = MutableLiveData<List<DBAuthor>>()
    val historyAuthor : MutableLiveData<List<DBAuthor>>
        get() = _historyAuthor

    fun loadAllAuthors(url: String) {
        val list = mutableListOf<Item>()
        Fuel.get(url).responseString { request, response, result ->
            val datas = Gson().fromJson(result.get(), AuthorList::class.java).datas
            //Why 为什么要这样做?
            val fields = datas::class.java.declaredFields
            for (x in fields) {

                x.isAccessible = true

                if (x.type.canonicalName == "java.util.List") {
                    list.add(Item("0", x.name, "LABEL", x.name, "pic", "sumn"))
                    list.addAll(x.get(datas) as List<Item>)
                }
            }
            _allAuthor.value = list
        }
    }


    fun loadHistoryAuthors() {
        doAsync {
            _historyAuthor.value = AuthorDB.queryAuthor()
        }
    }
}
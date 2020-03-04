package com.lzx.guanchajava.POJO.bean.newsListWithBanner

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class TopicList(
        val data: Data,
        val code: Int,
        val msg: String
){
    class Deserializer : ResponseDeserializable<TopicList> {
        override fun deserialize(response: String): TopicList = Gson().fromJson(response, TopicList::class.java)
    }
}
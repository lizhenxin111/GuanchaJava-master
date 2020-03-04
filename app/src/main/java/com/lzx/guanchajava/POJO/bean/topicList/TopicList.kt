package com.lzx.guanchajava.POJO.bean.topicList

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class TopicList(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<TopicList> {
        override fun deserialize(response: String): TopicList = Gson().fromJson(response, TopicList::class.java)
    }
}
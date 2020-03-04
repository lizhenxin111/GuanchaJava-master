package com.lzx.guanchajava.POJO.bean.postTopicList

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class PostTopicList(
        val code: Int,
        val data: MutableList<Data>,
        val msg: String
){
    class Deserializer : ResponseDeserializable<PostTopicList> {
        override fun deserialize(response: String): PostTopicList= Gson().fromJson(response, PostTopicList::class.java)
    }
}
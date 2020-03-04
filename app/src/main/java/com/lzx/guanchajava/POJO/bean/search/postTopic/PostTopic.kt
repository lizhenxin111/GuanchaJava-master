package com.lzx.guanchajava.POJO.bean.search.postTopic

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class PostTopic(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<PostTopic> {
        override fun deserialize(response: String): PostTopic= Gson().fromJson(response, PostTopic::class.java)
    }
}
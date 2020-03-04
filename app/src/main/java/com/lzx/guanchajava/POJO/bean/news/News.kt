package com.lzx.guanchajava.POJO.bean.news

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class News(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<News> {
        override fun deserialize(response: String): News = Gson().fromJson(response, News::class.java)
    }
}
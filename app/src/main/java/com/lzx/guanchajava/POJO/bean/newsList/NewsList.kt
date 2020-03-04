package com.lzx.guanchajava.POJO.bean.newsList

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class NewsList(
        val code: Int,
        val data: Data,
        val msg: String
) {
    class Deserializer : ResponseDeserializable<NewsList> {
        override fun deserialize(response: String): NewsList = Gson().fromJson(response, NewsList::class.java)
    }
}
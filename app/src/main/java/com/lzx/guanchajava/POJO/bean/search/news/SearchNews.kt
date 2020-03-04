package com.lzx.guanchajava.POJO.bean.search.news

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class SearchNews(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<SearchNews> {
        override fun deserialize(response: String): SearchNews= Gson().fromJson(response, SearchNews::class.java)
    }
}
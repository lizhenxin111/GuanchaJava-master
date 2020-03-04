package com.lzx.guanchajava.POJO.bean.search.post

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class SearchPost(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<SearchPost> {
        override fun deserialize(response: String): SearchPost= Gson().fromJson(response, SearchPost::class.java)
    }
}
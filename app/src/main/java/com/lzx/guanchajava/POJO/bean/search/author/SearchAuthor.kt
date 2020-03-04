package com.lzx.guanchajava.POJO.bean.search.author

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class SearchAuthor(
        val code: Int,
        val data: Data,
        val msg: String
) {
    class Deserializer : ResponseDeserializable<SearchAuthor> {
        override fun deserialize(response: String): SearchAuthor= Gson().fromJson(response, SearchAuthor::class.java)
    }
}
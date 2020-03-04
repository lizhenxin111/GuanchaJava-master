package com.lzx.guanchajava.POJO.bean.recomend.news

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class NewsRecomend(
        val code: Int,
        val datas: Datas
){
    class Deserializer : ResponseDeserializable<NewsRecomend> {
        override fun deserialize(response: String): NewsRecomend = Gson().fromJson(response, NewsRecomend::class.java)
    }
}
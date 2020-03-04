package com.lzx.guanchajava.POJO.bean.operate.praiseArticle

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class PraiseTopic(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<PraiseTopic> {
        override fun deserialize(response: String): PraiseTopic= Gson().fromJson(response, PraiseTopic::class.java)
    }
}
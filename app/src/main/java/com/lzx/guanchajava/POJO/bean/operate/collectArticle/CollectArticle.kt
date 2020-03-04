package com.lzx.guanchajava.POJO.bean.operate.collectArticle

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class CollectArticle(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<CollectArticle> {
        override fun deserialize(response: String): CollectArticle = Gson().fromJson(response, CollectArticle::class.java)
    }
}
package com.lzx.guanchajava.POJO.bean.search.newsTopic

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class NewsTopic(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<NewsTopic> {
        override fun deserialize(response: String): NewsTopic= Gson().fromJson(response, NewsTopic::class.java)
    }
}
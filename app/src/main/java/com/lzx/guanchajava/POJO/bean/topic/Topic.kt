package com.lzx.guanchajava.POJO.bean.topic

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Topic(
        val code: Int,
        val data: List<Data>,
        val msg: String
){
    class Deserializer : ResponseDeserializable<Topic> {
        override fun deserialize(response: String): Topic= Gson().fromJson(response, Topic::class.java)
    }
}
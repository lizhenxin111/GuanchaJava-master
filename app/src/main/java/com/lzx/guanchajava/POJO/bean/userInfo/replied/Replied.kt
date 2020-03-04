package com.lzx.guanchajava.POJO.bean.userInfo.replied

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Replied(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<Replied> {
        override fun deserialize(response: String): Replied= Gson().fromJson(response, Replied::class.java)
    }
}
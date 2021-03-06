package com.lzx.guanchajava.POJO.bean.operate.treadComment

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class TreadComment(
        val code: Int,
        val `data`: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<TreadComment> {
        override fun deserialize(response: String): TreadComment= Gson().fromJson(response, TreadComment::class.java)
    }
}
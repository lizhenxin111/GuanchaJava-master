package com.lzx.guanchajava.POJO.bean.operate.publishComment

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class PublishComment(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<PublishComment> {
        override fun deserialize(response: String): PublishComment= Gson().fromJson(response, PublishComment::class.java)
    }
}
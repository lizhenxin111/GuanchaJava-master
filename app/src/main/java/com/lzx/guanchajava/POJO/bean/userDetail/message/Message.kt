package com.lzx.guanchajava.POJO.bean.userDetail.message

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Message(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<Message> {
        override fun deserialize(response: String): Message= Gson().fromJson(response, Message::class.java)
    }
}
package com.lzx.guanchajava.POJO.bean.userDetail.message

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class SendMessage(
        val code: Int,
        val msg: String,
        val data: List<Any>
){
    class Deserializer : ResponseDeserializable<SendMessage> {
        override fun deserialize(response: String): SendMessage= Gson().fromJson(response, SendMessage::class.java)
    }
}
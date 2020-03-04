package com.lzx.guanchajava.POJO.bean.userDetail.reply

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class UserReply(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<UserReply> {
        override fun deserialize(response: String): UserReply= Gson().fromJson(response, UserReply::class.java)
    }
}
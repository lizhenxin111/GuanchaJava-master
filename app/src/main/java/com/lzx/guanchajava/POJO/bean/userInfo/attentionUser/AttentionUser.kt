package com.lzx.guanchajava.POJO.bean.userInfo.attentionUser

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class AttentionUser(
        val code: Int,
        val data: MutableList<Data>,
        val msg: String
){
    class Deserializer : ResponseDeserializable<AttentionUser> {
        override fun deserialize(response: String): AttentionUser= Gson().fromJson(response, AttentionUser::class.java)
    }
}
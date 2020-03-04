package com.lzx.guanchajava.POJO.bean.userDetail.profile

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class UserDetail(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<UserDetail> {
        override fun deserialize(response: String): UserDetail= Gson().fromJson(response, UserDetail::class.java)
    }
}
package com.lzx.guanchajava.POJO.bean.userDetail.userInfo

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class UserInfo(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<UserInfo> {
        override fun deserialize(response: String): UserInfo= Gson().fromJson(response, UserInfo::class.java)
    }
}
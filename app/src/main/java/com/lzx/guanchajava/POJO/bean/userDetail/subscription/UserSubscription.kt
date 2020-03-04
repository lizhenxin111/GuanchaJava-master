package com.lzx.guanchajava.POJO.bean.userDetail.subscription

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class UserSubscription(
        val code: Int,
        val data: List<Data>,
        val msg: String
){
    class Deserializer : ResponseDeserializable<UserSubscription> {
        override fun deserialize(response: String): UserSubscription= Gson().fromJson(response, UserSubscription::class.java)
    }
}
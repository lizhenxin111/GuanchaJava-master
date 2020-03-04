package com.lzx.guanchajava.POJO.bean.userDetail.collection

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class UserCollection(
        val code: Int,
        val data: Data,
        val msg: String
) {
    class Deserializer : ResponseDeserializable<UserCollection> {
        override fun deserialize(response: String): UserCollection = Gson().fromJson(response, UserCollection::class.java)
    }
}
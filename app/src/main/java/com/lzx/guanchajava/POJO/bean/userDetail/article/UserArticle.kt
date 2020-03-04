package com.lzx.guanchajava.POJO.bean.userDetail.article

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class UserArticle(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<UserArticle> {
        override fun deserialize(response: String): UserArticle= Gson().fromJson(response, UserArticle::class.java)
    }
}
package com.lzx.guanchajava.POJO.bean.author

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class AuthorList(
        val code: Int,
        val count: Int,
        val datas: Datas,
        val message: String
) {
    class Deserializer : ResponseDeserializable<AuthorList> {
        override fun deserialize(response: String): AuthorList = Gson().fromJson(response, AuthorList::class.java)
    }
}

package com.lzx.guanchajava.POJO.bean.authorDetail

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class AuthorDetail(
        val code: Int,
        val count: Int,
        val datas: MutableList<Data>,
        val feng: MutableList<Data>,
        val message: String
) {
    class Deserializer : ResponseDeserializable<AuthorDetail> {
        override fun deserialize(response: String): AuthorDetail = Gson().fromJson(response, AuthorDetail::class.java)
    }
}
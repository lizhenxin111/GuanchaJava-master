package com.lzx.guanchajava.POJO.bean.login

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Login(
        val code: Int,
        val data: Data,
        val msg: String
) {
    class Deserializer : ResponseDeserializable<Login> {
        override fun deserialize(response: String): Login = Gson().fromJson(response, Login::class.java)
    }
} //code=0成功，code=4失败
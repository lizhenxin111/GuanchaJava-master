package com.lzx.guanchajava.POJO.bean.userInfo.tokenValid

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class TokenState(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<TokenState> {
        override fun deserialize(response: String): TokenState= Gson().fromJson(response, TokenState::class.java)
    }
}
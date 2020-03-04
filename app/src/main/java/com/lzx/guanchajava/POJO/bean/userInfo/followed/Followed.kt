package com.lzx.guanchajava.POJO.bean.userInfo.followed

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Followed(
        val code: Int,
        val data: MutableList<Data>,
        val msg: String
){
    class Deserializer : ResponseDeserializable<Followed> {
        override fun deserialize(response: String): Followed= Gson().fromJson(response, Followed::class.java)
    }
}
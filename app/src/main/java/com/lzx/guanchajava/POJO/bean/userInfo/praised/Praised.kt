package com.lzx.guanchajava.POJO.bean.userInfo.praised

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Praised(
        val code: Int,
        val data: MutableList<Data>,
        val msg: String
){
    class Deserializer : ResponseDeserializable<Praised> {
        override fun deserialize(response: String): Praised= Gson().fromJson(response, Praised::class.java)
    }
}
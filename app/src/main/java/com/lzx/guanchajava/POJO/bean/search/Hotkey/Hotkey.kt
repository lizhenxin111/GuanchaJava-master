package com.lzx.guanchajava.POJO.bean.search.Hotkey

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Hotkey(
        val code: Int,
        val data: List<Data>,
        val msg: String
){
    class Deserializer : ResponseDeserializable<Hotkey> {
        override fun deserialize(response: String): Hotkey= Gson().fromJson(response, Hotkey::class.java)
    }
}
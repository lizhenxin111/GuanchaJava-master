package com.lzx.guanchajava.POJO.bean.edit

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Edit(
        val code: Int,
        val data: List<Any>,
        val msg: String
) {
    class Deserializer : ResponseDeserializable<Edit> {
        override fun deserialize(response: String): Edit = Gson().fromJson(response, Edit::class.java)
    }
}
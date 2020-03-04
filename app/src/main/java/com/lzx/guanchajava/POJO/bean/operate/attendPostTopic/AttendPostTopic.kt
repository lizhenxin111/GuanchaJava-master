package com.lzx.guanchajava.POJO.bean.operate.attendPostTopic

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class AttendPostTopic(
        val code: Int,
        val msg: String,
        val data: Data
) {
    class Deserializer : ResponseDeserializable<AttendPostTopic> {
        override fun deserialize(response: String): AttendPostTopic = Gson().fromJson(response, AttendPostTopic::class.java)
    }
}
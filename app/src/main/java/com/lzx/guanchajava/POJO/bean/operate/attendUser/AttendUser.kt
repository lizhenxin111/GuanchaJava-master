package com.lzx.guanchajava.POJO.bean.operate.attendUser

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class AttendUser(
        val code: Int,
        val msg: String,
        val data: Data
){
    class Deserializer : ResponseDeserializable<AttendUser> {
        override fun deserialize(response: String): AttendUser= Gson().fromJson(response, AttendUser::class.java)
    }
}
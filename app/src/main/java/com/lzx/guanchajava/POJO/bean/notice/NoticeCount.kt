package com.lzx.guanchajava.POJO.bean.notice

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class NoticeCount(
        val code: Int,
        val data: Data,
        val msg: String
) {
    class Deserializer : ResponseDeserializable<NoticeCount> {
        override fun deserialize(response: String): NoticeCount = Gson().fromJson(response, NoticeCount::class.java)
    }
}
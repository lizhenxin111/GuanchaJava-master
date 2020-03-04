package com.lzx.guanchajava.POJO.bean.operate.praiseComment

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class PraiseComment(
        val code: Int,
        val `data`: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<PraiseComment> {
        override fun deserialize(response: String): PraiseComment= Gson().fromJson(response, PraiseComment::class.java)
    }
}
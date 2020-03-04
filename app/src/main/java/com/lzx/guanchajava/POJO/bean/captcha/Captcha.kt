package com.lzx.guanchajava.POJO.bean.captcha

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Captcha(
        val code: Int,
        val data: List<Any>,
        val msg: String
) {
    class Deserializer : ResponseDeserializable<Captcha> {
        override fun deserialize(response: String): Captcha = Gson().fromJson(response, Captcha::class.java)
    }
}
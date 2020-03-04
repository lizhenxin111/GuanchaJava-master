package com.lzx.guanchajava.POJO.bean.tagList

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class TagList(
        val code: Int,
        val data: Data,
        val msg: String
){
    class Deserializer : ResponseDeserializable<TagList> {
        override fun deserialize(response: String): TagList= Gson().fromJson(response, TagList::class.java)
    }
}
package com.lzx.guanchajava.POJO.bean.voteResult

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.bean.topic.VoteInfo

data class VoteResult(
        val code: Int,
        val data: List<VoteInfo>,
        val msg: String
){
    class Deserializer : ResponseDeserializable<VoteResult> {
        override fun deserialize(response: String): VoteResult= Gson().fromJson(response, VoteResult::class.java)
    }
}
package com.lzx.guanchajava.POJO.bean.reply

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Reply(

        val id: Int,            //本条回复的id
        val code_id: String,    //文章id
        val parent_id: Int,     //被回复的id
        val root_id: Int,       //盖楼的根id
        val code_type: String,
        val created_at: String,
        val ex_nick: Any?,
        val fold: Int,
        val from: String,
        val from_weibo: Boolean,
        val has_floor: Boolean,
        val has_praise: Boolean,
        val has_tread: Boolean,
        val parent: MutableList<Reply>,
        val praise_num: Int,
        val reply_count: Int,
        val to_user_id: Int,
        val tread_num: String,
        val user_description: String,
        val user_id: Int,
        val user_level: String,
        val user_level_logo: String,
        val user_nick: String,
        val user_photo: String
) : BaseReply(){
    class Deserializer : ResponseDeserializable<Reply> {
        override fun deserialize(response: String): Reply = Gson().fromJson(response, Reply::class.java)
    }
}
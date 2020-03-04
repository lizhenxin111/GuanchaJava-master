package com.lzx.guanchajava.POJO.bean.userDetail.reply

data class ParentComment(
        val code_id: String,
        val code_type: String,
        val content: String,
        val created_at: String,
        val from: Int,
        val has_praise: Boolean,
        val has_tread: Boolean,
        val id: Int,
        val parent_id: Int,
        val praise_count: Int,
        val praise_num1: String,
        val reply_count: Int,
        val root_id: Int,
        val status: Int,
        val to_user_id: Int,
        val tread_count: Int,
        val tread_num: String,
        val user_description: String,
        val user_id: Int,
        val user_level: String,
        val user_level_logo: String,
        val user_nick: String,
        val user_photo: String
)
package com.lzx.guanchajava.POJO.bean.userDetail.message

data class Item(
        val avatar: String,
        val content: String,
        val created_at: String,
        val receiver_id: Int,
        val sender_id: Int,
        val unread_nums: Int,
        val user_description: String,
        val user_level_logo: String,
        val user_nick: String
)
package com.lzx.guanchajava.POJO.bean.userInfo.followed

data class Data(
        val action: String,
        val code_id: String,
        val content: String,
        val created_at: String,
        val has_attention: Boolean,
        val is_read: Boolean,
        val post_id: Int,
        val post_type: Int,
        val start: String,
        val type: String,
        val user_level_logo: String,
        val user_photo: String
)
package com.lzx.guanchajava.POJO.bean.userDetail.article

data class Item(
        val access_device: Int,
        val action_created_at: String,
        val code_type: Int,
        val collection_count: String,
        val comment_count: Int,
        val created_at: String,
        val has_collection: Boolean,
        val has_praise: Boolean,
        val has_vote: Boolean,
        val id: Int,
        val is_anonymous: Boolean,
        val orderby_value: Int,
        val pass_at: String,
        val pic: String,
        val post_url: String,
        val post_url_night: String,
        val praise_num: Int,
        val share_url: String,
        val status: Int,
        val summary: String,
        val title: String,
        val user_description: String,
        val user_id: Int,
        val user_level: String,
        val user_level_logo: String,
        val user_nick: String,
        val user_photo: String,
        val view_count: Int
)
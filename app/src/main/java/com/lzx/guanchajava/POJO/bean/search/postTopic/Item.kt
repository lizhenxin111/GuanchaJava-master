package com.lzx.guanchajava.POJO.bean.search.postTopic

data class Item(
        val attention_nums: String,
        val description: String,
        val has_attention: Boolean,
        val post_nums: Int,
        val topic_id: String,
        val topic_img: String,
        val topic_name: String
)
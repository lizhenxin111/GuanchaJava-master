package com.lzx.guanchajava.POJO.bean.reply

data class Data(
        val all_hot_count: Int,
        val author_id: Int,
        val count: String,
        val hot_count: Int,
        val hots: MutableList<Reply>,
        val items: MutableList<Reply>,
        val pic_allow: Boolean,
        val status: Int,
        val tread: Int,
        val it: Reply
)
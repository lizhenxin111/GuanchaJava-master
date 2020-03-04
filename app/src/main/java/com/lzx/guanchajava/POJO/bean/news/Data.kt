package com.lzx.guanchajava.POJO.bean.news

data class Data(
        val author: MutableList<Author>?,
        val collection_num: Int,
        val comment_num: Int,
        val content: String,
        val daodu: String,
        val editor: Editor,
        val has_collection: Boolean,
        val id: String,
        val news_time: String,
        val show_editor: Boolean,
        val source: String,
        val title: String
)
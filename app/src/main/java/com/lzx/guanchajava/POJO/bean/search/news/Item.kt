package com.lzx.guanchajava.POJO.bean.search.news

data class Item(
        val comment_count: Int,
        val created_at: String,
        val id: Int,
        val redirect_link: String,
        val special: String,
        val special_url: String,
        val summary: String,
        val title: String,
        val type: String,
        val url: String,
        val view_count: Int
)
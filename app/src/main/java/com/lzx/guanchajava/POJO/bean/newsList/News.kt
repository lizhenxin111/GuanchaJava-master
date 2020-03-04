package com.lzx.guanchajava.POJO.bean.newsList

import com.lzx.guanchajava.POJO.bean.BaseNewsItem

data class News(
        val author: Author,
        val comment_num: Int,
        val id: String,
        val news_time: String,
        val news_type: Int,
        val pic: String,
        val show_type: Int,
        val summary: String,
        val special: Special,
        val title: String,
        val url: String,
        val user: User
) : BaseNewsItem()
package com.lzx.guanchajava.POJO.bean.authorDetail

data class Data(
        val author: MutableList<Author>,
        val clicktype: Int,
        val comments: String,
        val fengwen_id: String,
        val format_date: String,
        val id: String,
        val mobile_url: String,
        val news_time: String,
        val out_link: String,
        val pic: String,
        val pic_l: String,
        val pic_more: String,
        val preview_n: String,
        val redirect_link: String,
        val special: MutableList<Special>,
        val summary: String,
        val title: String,
        val type: String
)
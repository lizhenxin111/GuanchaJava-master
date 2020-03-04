package com.lzx.guanchajava.POJO.bean.newsListWithBanner

import com.lzx.guanchajava.POJO.bean.newsList.News

data class Data(
        val items: MutableList<News>,
        val toutiao: MutableList<News>
)
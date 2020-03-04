package com.lzx.guanchajava.POJO.bean.db

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

data class DBHistory (
        @Column(nullable = false, unique = true, ignore = false)
        val article_id: String,
        val article_type: String,
        val title: String,
        val open_time: String,
        val close_time: String
) : LitePalSupport()
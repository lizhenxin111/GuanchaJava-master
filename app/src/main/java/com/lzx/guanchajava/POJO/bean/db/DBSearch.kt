package com.lzx.guanchajava.POJO.bean.db

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

data class DBSearch(
        @Column(unique = true)
        val word: String
) : LitePalSupport()
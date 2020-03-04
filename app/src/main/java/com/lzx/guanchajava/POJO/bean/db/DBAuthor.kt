package com.lzx.guanchajava.POJO.bean.db

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

data class DBAuthor(
        @Column(unique = true, ignore = false)
        val author_id: String,
        val name: String,
        val pic: String,
        val cate: String,
        val des: String,
        val sum: Int
) : LitePalSupport()  //数据类的属性和数据库的键意义对应且顺序相同
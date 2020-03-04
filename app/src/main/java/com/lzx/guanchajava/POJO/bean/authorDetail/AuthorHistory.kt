package com.lzx.guanchajava.POJO.bean.authorDetail

data class AuthorHistory(
        val id: String,
        val name: String,
        val pic: String,
        val cate: String,
        val des: String,
        val sum: Int
)   //数据类的属性和数据库的键意义对应且顺序相同
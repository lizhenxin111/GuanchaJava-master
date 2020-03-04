package com.lzx.guanchajava.POJO.bean.login

data class User(
        val avatar: String,
        val comment_count: Int,
        val mobile: String,
        val msg_count: Int,
        val phone_code: Int,
        val uid: Int,
        val user_description: String,
        val user_level_logo: String,
        val user_nick: String,
        val token: String   //该属性不在User类中,在Login/Data类中,但读取时按照在User类中读取
)
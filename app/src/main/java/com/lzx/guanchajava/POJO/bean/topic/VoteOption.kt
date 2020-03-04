package com.lzx.guanchajava.POJO.bean.topic

data class VoteOption(
        val has_voted: Boolean,
        val option_id: Int,
        val option_name: String,
        val vote_nums: String,
        var vote_sum: Int
)
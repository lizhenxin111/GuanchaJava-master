package com.lzx.guanchajava.POJO.bean.topic

data class VoteInfo(
        val has_voted: Boolean,
        val total_vote: Int,
        val vote_id: Int,
        val vote_limit: Int,
        val vote_options: MutableList<VoteOption>,
        val vote_title: String,
        val vote_type: String
)
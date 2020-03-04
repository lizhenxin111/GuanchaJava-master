package com.lzx.guanchajava.member.bean

/**
 * 文章历史记录
 * @property article_id String
 * @property article_type String
 * @property article_title String
 * @constructor
 */
data class Article (
        var article_id: String,
        var article_type: String,
        var article_title: String
)
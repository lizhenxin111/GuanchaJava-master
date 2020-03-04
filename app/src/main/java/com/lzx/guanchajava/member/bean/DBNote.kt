package com.lzx.guanchajava.member.bean

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * 笔记
 * 只做本地存储
 * @property article_id String
 * @property article_type String
 * @property article_title String
 * @property content String
 * @property note String
 * @constructor
 */
data class DBNote(
        var article_id: String,
        var article_type: String,
        var article_title: String,
        @Column(unique = true)
        var content: String,            //被标记的内容
        var note: String                //笔记内容
) : LitePalSupport()
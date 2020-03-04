package com.lzx.guanchajava.member.bean

import org.litepal.crud.LitePalSupport

/**
 * 收藏的评论
 * @property title String
 * @property code_id String
 * @property code_type String
 * @property user_photo String
 * @property user_id String
 * @property user_nick String
 * @property content String
 * @constructor
 */
data class DBComment(
        var title : String,
        var code_id : String,
        var code_type : String,
        var user_photo : String,
        var user_id : String,
        var user_nick : String,
        var content : String
) : LitePalSupport()
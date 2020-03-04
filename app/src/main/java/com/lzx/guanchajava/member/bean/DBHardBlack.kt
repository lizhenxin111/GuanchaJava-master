package com.lzx.guanchajava.member.bean

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * 超级黑名单
 * @property user_id String
 * @property user_nick String
 * @property user_photo String
 * @property blacked Boolean
 * @constructor
 */
data class DBHardBlack(
        @Column(unique = true)
        val user_id: String,
        val user_nick: String,
        val user_photo: String,
        var blacked: Boolean
) : LitePalSupport()
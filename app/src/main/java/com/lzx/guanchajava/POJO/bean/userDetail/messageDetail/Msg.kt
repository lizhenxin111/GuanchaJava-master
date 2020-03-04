package com.lzx.guanchajava.POJO.bean.userDetail.messageDetail

data class Msg(
        var content: String,
        var created_at: String,
        var has_accusation: Boolean,
        var id: Int,
        var receiver_id: Int,
        var sender_id: Int,
        var isSender: Boolean,
        var avater: String
)
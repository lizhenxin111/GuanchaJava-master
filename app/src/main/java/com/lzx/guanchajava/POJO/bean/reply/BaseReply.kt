package com.lzx.guanchajava.POJO.bean.reply

open class BaseReply() {
    var content: String = ""
    var status: Int = 0
    var showMore: Boolean = true

    constructor(status: Int, content: String) : this() {
        this.status = status
        this.content = content
    }
}
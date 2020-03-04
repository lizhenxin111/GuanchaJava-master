package com.lzx.guanchajava.POJO.bean.userDetail.messageDetail

data class Items(
        val msgs: List<Msg>,
        val `receiver`: List<Receiver>,
        val sender: List<Sender>
)
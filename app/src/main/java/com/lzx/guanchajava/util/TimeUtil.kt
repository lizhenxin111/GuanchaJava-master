package com.lzx.guanchajava.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    fun timeAll() : String {
        return SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Date())
    }

    fun timeMinute() : String {
        return SimpleDateFormat("yyyy-MM-dd HH-mm").format(Date())
    }


}
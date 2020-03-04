package com.lzx.guanchajava.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object CopyUtil {
    /**
     * 将选中的内容复制到剪贴板
     * @param content String    选中的内容
     */
    fun copyToClipboard(content: String) {
        val clipboardManager = App.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("LABEL_COPY_LINK", content)
        clipboardManager.primaryClip = clipData
    }
}
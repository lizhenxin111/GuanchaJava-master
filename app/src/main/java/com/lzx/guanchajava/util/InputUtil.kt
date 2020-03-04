package com.lzx.guanchajava.util

import android.content.Context
import android.view.inputmethod.InputMethodManager

object InputUtil {
    fun toggleKeyboardState() {
        val imm = App.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
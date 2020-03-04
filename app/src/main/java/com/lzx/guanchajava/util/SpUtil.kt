package com.lzx.guanchajava.util

import android.app.Activity
import android.content.Context

const val SETTING = "SETTING"

object SpUtil {
    private val PREF_FIRST_OPEN = "_FIRST_OPEN_"
    private val PREF_AGREE_TERMS = "_AGREE_TERMS_"

    fun firstOpen(context: Context): Boolean {
        return if (context.getSharedPreferences(SETTING, Activity.MODE_PRIVATE).getBoolean(PREF_FIRST_OPEN, true)) {
            context.getSharedPreferences(SETTING, Activity.MODE_PRIVATE).edit().
                    putBoolean(PREF_FIRST_OPEN, false).
                    apply()
            true
        } else false
    }

    fun agreeTerms() : Boolean {
        return App.context.getSharedPreferences(SETTING, Activity.MODE_PRIVATE).getBoolean(PREF_AGREE_TERMS, false)
    }
    fun setAgreeTerms(agree: Boolean) {
        App.context.getSharedPreferences(SETTING, Activity.MODE_PRIVATE).edit().
                putBoolean(PREF_AGREE_TERMS, agree).
                apply()
    }
}
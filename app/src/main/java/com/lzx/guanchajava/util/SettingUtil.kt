package com.lzx.guanchajava.util

import org.jetbrains.anko.defaultSharedPreferences

object SettingUtil {

    fun isNightMode() = getBoolean("pref_night_mode", false)
    fun isNoPic() = getBoolean("pref_no_pic", false)
    fun isReceiveNotice() = getBoolean("pref_receive_notice", true)
    fun isAutoCheckUpdate() = getBoolean("pref_auto_check_update", true)
    fun isShowRecommend() = getBoolean("pref_show_recommend", true)
    fun getTextSize() = App.context.defaultSharedPreferences.getString("pref_text_size", "16")
    fun isMember() = getBoolean("pref_member", false)

    private fun getBoolean(key: String, defValue: Boolean) = App.context.defaultSharedPreferences.getBoolean(key, defValue)
}
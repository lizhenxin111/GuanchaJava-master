package com.lzx.guanchajava.view.fragment.setting

import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.UserInfoActivity
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.util.LoginUtil
import com.lzx.guanchajava.util.ReceiverUtil
import com.lzx.guanchajava.util.ResourceUtil
import com.lzx.guanchajava.view.widget.InfoDialog
import com.tencent.bugly.beta.Beta
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class SettingsFragment : PreferenceFragmentCompat(), androidx.preference.Preference.OnPreferenceChangeListener, androidx.preference.Preference.OnPreferenceClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        findPreference<androidx.preference.Preference>("pref_clear_cache")?.summary = "${ResourceUtil.getAppCacheSize() / 1000L / 1000L}M"

        findPreference<androidx.preference.Preference>("pref_check_update")?.onPreferenceClickListener = this
        findPreference<androidx.preference.Preference>("pref_clear_cache")?.onPreferenceClickListener = this
        findPreference<androidx.preference.Preference>("pref_black_list")?.onPreferenceClickListener = this
        findPreference<androidx.preference.Preference>("pref_about")?.onPreferenceClickListener = this

        findPreference<SwitchPreference>("pref_night_mode")?.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(preference: androidx.preference.Preference?, newValue: Any?): Boolean {
        when(preference!!.key) {
            "pref_night_mode" -> {
                var msg = "已"
                if (newValue == true) {
                    preference.sharedPreferences.edit().putBoolean("pref_night_mode", true).apply()
                    msg += "打开"
                } else {
                    preference.sharedPreferences.edit().putBoolean("pref_night_mode", false).apply()
                    msg += "关闭"
                }
                Toast.makeText(App.context, "${msg}夜间模式", Toast.LENGTH_SHORT).show()
                ReceiverUtil.sendShowModeBroadcast()
            }
            "pref_no_pic" -> {
                ReceiverUtil.sendPicBroadcast()
            }
        }
        return true
    }

    override fun onPreferenceClick(preference: androidx.preference.Preference?): Boolean {
        when (preference!!.key) {
            "pref_check_update" -> {
                Beta.checkUpgrade()
            }
            "pref_clear_cache" -> {
                ResourceUtil.clearAppCache()
                App.context.toast("清除全部缓存")
            }
            "pref_black_list" -> {
                if (LoginUtil.hasToken()) startActivity<UserInfoActivity>()
                else toast("请登录")
            }
            "pref_about" -> {
                InfoDialog().show(context!!)
            }
        }
        return true
    }
}
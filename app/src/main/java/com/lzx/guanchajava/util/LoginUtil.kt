package com.lzx.guanchajava.util

import android.content.Context
import com.lzx.guanchajava.POJO.bean.login.User
import com.lzx.guanchajava.POJO.bean.userDetail.userInfo.Data

const val LOGIN = "LOGIN_PREFERENCE"
const val LOGIN_STATE = "LOGIN_STATE_PREFERENCE"
const val INFO = "INFO_PREFERENCE"


object LoginUtil {

    fun saveLogin(user: User, token: String) : Boolean {
        val editor = App.context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE).edit()
        editor.putString("user_photo", user.avatar)
        editor.putInt("comment_count", user.comment_count)
        editor.putString("mobile", user.mobile)
        editor.putInt("msg_count", user.msg_count)
        editor.putInt("phone_code", user.phone_code)
        editor.putInt("uid", user.uid)
        editor.putString("user_description", user.user_description)
        editor.putString("user_level_logo", user.user_level_logo)
        editor.putString("user_nick", user.user_nick)
        editor.putString("token", token)
        return editor.commit()
    }

    fun getLogin() : User {
        val pref = App.context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)

        return User(pref.getString("avatar", ""),
                pref.getInt("comment_count", -1),
                pref.getString("mobile", ""),
                pref.getInt("msg_count", -1),
                pref.getInt("phone_code", -1),
                pref.getInt("uid", -1),
                pref.getString("user_description", ""),
                pref.getString("user_level_logo", ""),
                pref.getString("user_nick", ""),
                pref.getString("token", "")
        )
    }

    fun clearLogin() {
        App.context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE).edit().clear().commit()
        App.context.getSharedPreferences(INFO, Context.MODE_PRIVATE).edit().clear().commit()
    }

    fun checkLogin() {
        //if (!hasToken()) App.context.toast("请登录")
    }

    fun hasToken() : Boolean {
        return App.context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE).contains("token")
    }

    fun getToken() : String {
        if (App.context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE).getString("token", "").isNullOrEmpty()) {
            checkLogin()
        }
        return App.context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE).getString("token", "")
    }


    fun getUid() : Int {
        return App.context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE).getInt("uid", -1)
    }

    fun saveInfo(info : Data) {
        val editor = App.context.getSharedPreferences(INFO, Context.MODE_PRIVATE).edit()
        editor.putString("attention_nums", info.attention_nums)
        editor.putString("fans_nums", info.fans_nums)
        editor.putBoolean("has_attention", info.has_attention)
        editor.putBoolean("has_black", info.has_black)
        editor.putString("home_page", info.home_page)
        editor.putInt("uid", info.id)
        editor.putInt("post_nums", info.post_nums)
        editor.putString("user_address", info.user_address)
        editor.putString("user_birthdate", info.user_birthdate)
        editor.putString("user_description", info.user_description)
        editor.putString("user_graduate", info.user_graduate)
        editor.putString("user_level_logo", info.user_level_logo)
        editor.putString("user_nick", info.user_nick)
        editor.putString("user_photo", info.user_photo)
        editor.putString("user_profession", info.user_profession)
        editor.putInt("user_sex", info.user_sex)
        editor.commit()
    }

    fun getInfo() : Data {
        val pref = App.context.getSharedPreferences(INFO, Context.MODE_PRIVATE)
        return Data(
                pref.getString("attention_nums", ""),
                pref.getString("fans_nums", ""),
                pref.getBoolean("has_attention", false),
                pref.getBoolean("has_black", false),
                pref.getString("home_page", ""),
                pref.getInt("uid", -1),
                pref.getInt("post_nums", -1),
                pref.getString("user_address", ""),
                pref.getString("user_birthdate", ""),
                pref.getString("user_description", ""),
                pref.getString("user_graduate", ""),
                pref.getString("user_level_logo", ""),
                pref.getString("user_nick", ""),
                pref.getString("user_photo", ""),
                pref.getString("user_profession", ""),
                pref.getInt("user_sex", -1)
        )
    }


}
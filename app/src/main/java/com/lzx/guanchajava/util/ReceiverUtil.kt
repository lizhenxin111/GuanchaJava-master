package com.lzx.guanchajava.util

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.lzx.guanchajava.POJO.ACTION_CHANGE_SHOW_MODE
import com.lzx.guanchajava.POJO.ACTION_LOGIN_IN
import com.lzx.guanchajava.POJO.ACTION_LOG_OUT
import com.lzx.guanchajava.view.activity.MainActivity
import com.lzx.guanchajava.view.widget.ACTION_LOAD_IMAGE
import com.lzx.guanchajava.view.widget.UrlImageView

object ReceiverUtil {


    //登录
    fun registerLoginReceiver(receiver: BroadcastReceiver) {
        val filter = IntentFilter().apply {
            addAction(ACTION_LOGIN_IN)
        }
        LocalBroadcastManager.getInstance(App.context).registerReceiver(receiver, filter)
    }
    fun unregisterLoginReceiver(receiver: BroadcastReceiver) {
        LocalBroadcastManager.getInstance(App.context).unregisterReceiver(receiver)
    }
    fun sendLoginInBroadcast() {
        Intent().also {
            it.action = ACTION_LOGIN_IN
            LocalBroadcastManager.getInstance(App.context).sendBroadcast(it)
        }
    }


    //退出
    fun registerLogoutReceiver(receiver: BroadcastReceiver) {
        val filter = IntentFilter().apply {
            addAction(ACTION_LOG_OUT)
        }
        LocalBroadcastManager.getInstance(App.context).registerReceiver(receiver, filter)
    }
    fun unregisterLogoutReceiver(receiver: BroadcastReceiver) {
        LocalBroadcastManager.getInstance(App.context).unregisterReceiver(receiver)
    }
    fun sendLogoutBroadcast() {
        Intent().also {
            it.action = ACTION_LOG_OUT
            LocalBroadcastManager.getInstance(App.context).sendBroadcast(it)
        }
    }


    //夜间模式
    fun registerShowModeReceiver(receiver: BroadcastReceiver) {
        val filter = IntentFilter().apply {
            addAction(ACTION_CHANGE_SHOW_MODE)
        }
        LocalBroadcastManager.getInstance(App.context).registerReceiver(receiver, filter)
    }
    fun unregisterShowModeReceiver(receiver: BroadcastReceiver) {
        LocalBroadcastManager.getInstance(App.context).unregisterReceiver(receiver)
    }
    fun sendShowModeBroadcast() {
        LocalBroadcastManager.getInstance(App.context).sendBroadcast(Intent(ACTION_CHANGE_SHOW_MODE).apply {
            component = ComponentName("com.lzx.guanchajava", "com.lzx.guanchajava.view.activity.MainActivity.LocalReceiver")
            setClass(App.context, MainActivity.LocalReceiver::class.java)
        })
    }


    //无图模式
    fun registerPicReceiver(receiver: BroadcastReceiver) {
        val filter = IntentFilter().apply {
            addAction(ACTION_LOAD_IMAGE)
        }
        LocalBroadcastManager.getInstance(App.context).registerReceiver(receiver, filter)
    }
    fun unregisterPicReceiver(receiver: BroadcastReceiver) {
        LocalBroadcastManager.getInstance(App.context).unregisterReceiver(receiver)
    }
    fun sendPicBroadcast() {
        //LocalBroadcastManager.getInstance(App.context).sendBroadcast(Intent(ACTION_LOAD_IMAGE))
        LocalBroadcastManager.getInstance(App.context).sendBroadcast(Intent(ACTION_LOAD_IMAGE).apply {
            component = ComponentName("com.lzx.guanchajava", "com.lzx.guanchajava.view.widget.UrlImageView.ImageBroadcastReceiver")
            setClass(App.context, UrlImageView.ImageBroadcastReceiver::class.java)
        })
    }
}
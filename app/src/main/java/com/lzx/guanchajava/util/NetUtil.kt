package com.lzx.guanchajava.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


object NetUtil {
    fun isNetConnect() : Boolean {

        val mConnectivityManager = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (mConnectivityManager != null) {
            val mNetwordInfo = mConnectivityManager.activeNetworkInfo
            if (mNetwordInfo != null && mNetwordInfo.isConnected) {
                if (mNetwordInfo.detailedState == NetworkInfo.DetailedState.CONNECTED) {
                    return true
                }
            }
        }
        return false

    }

    fun isNetworkAvailable() : Boolean{
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        val connectivityManager = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager == null) {
            return false
        } else {
            // 获取NetworkInfo对象
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo.isConnected
        }
    }
}
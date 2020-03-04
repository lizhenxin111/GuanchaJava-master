package com.lzx.guanchajava.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import java.util.*

/**
 * Created by Smile on 2016/9/26.
 */

class NetworkChange : BroadcastReceiver() {

    val wifi = 2
    val mobile = 1
    val none = 0
    var oldState = none
    var onNetWorkChange: MutableList<OnNetWorkChange>? = ArrayList()

    //回调接口
    interface OnNetWorkChange {
        //返回各个（wifi,移动网络，没有网络）状态的值，上一个网络状态的值，当前的网络状态的值
        fun onChange(wifi: Int, mobile: Int, none: Int, oldStatus: Int, newStatus: Int)
    }

    /**
     * 增加网络变化监听回调对象
     * 如果设置多个回调，请务必不要设置相同名字的OnNetWorkChange对象，否则会无效
     *
     * @param onNetWorkChange 回调对象
     */
    fun setOnNetWorkChange(onNetWorkChange: OnNetWorkChange) {
        if (this.onNetWorkChange!!.contains(onNetWorkChange)) {
            return
        }
        this.onNetWorkChange!!.add(onNetWorkChange)
        Log.i("网络状态", "添加一个回调。已设置：" + this.onNetWorkChange!!.size)
    }

    /**
     * 取消网络变化监听监听回调
     *
     * @param onNetWorkChange 回调对象
     */
    fun delOnNetWorkChange(onNetWorkChange: OnNetWorkChange) {
        if (this.onNetWorkChange!!.contains(onNetWorkChange)) {
            this.onNetWorkChange!!.remove(onNetWorkChange)
            Log.i("网络状态", "删除一个回调。还有：" + this.onNetWorkChange!!.size)
        }
    }

    /**
     * 触发网络状态监听回调
     *
     * @param nowStatus 当前网络状态
     */
    private fun setChange(nowStatus: Int) {
        for (change in onNetWorkChange!!) {
            change.onChange(wifi, mobile, none, oldState, nowStatus)
        }
        oldState = nowStatus
    }

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (onNetWorkChange == null) {
            //当没有设置回调的时候，什么都不做
            return
        }
        if (!mobNetInfo.isConnected && !wifiNetInfo.isConnected) {
            Log.i("通知", "网络不可以用")
            setChange(none)
        } else if (mobNetInfo.isConnected) {
            Log.i("通知", "仅移动网络可用")
            setChange(mobile)
        } else if (wifiNetInfo.isConnected) {
            Log.i("通知", "Wifi网络可用")
            setChange(wifi)
        }
    }

    companion object {
        private var networkChange: NetworkChange? = null

        val instance: NetworkChange
            get() {
                if (networkChange == null) {
                    networkChange = NetworkChange()
                }
                return networkChange!!
            }
    }
}
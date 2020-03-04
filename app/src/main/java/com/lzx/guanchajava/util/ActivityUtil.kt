package com.lzx.guanchajava.util

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent


class ActivityUtil {
    companion object {
        private var activityList: MutableList<Activity>? = ArrayList()
        private lateinit var instance: ActivityUtil

        // 单例模式中获取唯一的ExitApplication实例
        @Synchronized
        fun getInstance(): ActivityUtil {
            if (!::instance.isInitialized) {
                instance = ActivityUtil()
            }
            return instance
        }

        fun startActivity(from: Activity, to: Class<*>, vararg extras: Pair<String, String>) {
            val intent = Intent(from, to)
            for (x in extras) {
                intent.putExtra(x.first, x.second)
            }
            from.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(from).toBundle())
        }
    }

    // 添加Activity到容器中
    internal fun addActivity(activity: Activity) {
        activityList?.add(activity)
    }

    // 移除Activity
    internal fun removeActivity(activity: Activity) {
        activityList?.remove(activity)
    }

    // 遍历所有Activity并finish
    internal fun exitSystem() {
        for (activity in activityList!!) {
            activity?.finish()
        }
        // 退出进程
        System.exit(0)
    }
}
package com.lzx.guanchajava.util

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent

object JumpUtil {
    fun startActivity(activity: Activity, cls: Class<*>, vararg extras: Pair<String, String>) {
        val intent = Intent(activity, cls)
        for (x in extras) {
            intent.putExtra(x.first, x.second)
        }
        activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
    }

    fun startActivity(context: Context, cls: Class<*>, vararg extras: Pair<String, String>) {
        val intent = Intent(context, cls)
        for (x in extras) {
            intent.putExtra(x.first, x.second)
        }
        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(context as Activity).toBundle())
    }

}
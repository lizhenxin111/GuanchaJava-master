package com.lzx.guanchajava.util

/*import cn.bmob.v3.Bmob*/
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.tencent.bugly.Bugly
import org.litepal.LitePal


class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context : Context
    }


    override fun onCreate() {
        super.onCreate()
        //Bmob.initialize(this, "e8900e00fcffac34af0607560e47407a")
        LitePal.initialize(this)
        context = applicationContext
        Bugly.init(applicationContext, "6b5ef0fe3b", false)
    }


}
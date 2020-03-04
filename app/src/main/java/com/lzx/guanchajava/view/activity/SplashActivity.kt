package com.lzx.guanchajava.view.activity

import android.content.Context
import android.os.Handler
import android.view.KeyEvent
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.SpUtil
import org.jetbrains.anko.startActivity


class SplashActivity : BaseActivity(){
    override fun loadUI() {
        setContentView(R.layout.activity_splash)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        firstOpen(applicationContext)

        mHideHandler.postDelayed(jumpRunnable, 2000)
    }

    override fun loadContent() {
    }

    override fun loadOther() {
    }

    private val mHideHandler = Handler()

    override fun checkNetwork(): Boolean {
        return false
    }

    /*首次打开的操作*/
    private fun firstOpen(context: Context) {
        if (SpUtil.firstOpen(context)) {

        }
    }


    private val jumpRunnable : Runnable = Runnable {
        startActivity<MainActivity>()
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}
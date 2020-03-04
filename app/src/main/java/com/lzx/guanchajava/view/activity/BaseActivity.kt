package com.lzx.guanchajava.view.activity

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.ActivityUtil
import com.lzx.guanchajava.util.NetUtil
import com.lzx.guanchajava.util.ResourceUtil

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var mLoadingProgressBar : AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityUtil.getInstance().addActivity(this)
        window.enterTransition = Explode().setDuration(800)
        //window.exitTransition = Fade().setDuration(500)
        window.returnTransition = Fade().setDuration(600)
        //window.reenterTransition = Fade().setDuration(500)
        super.onCreate(savedInstanceState)

        initLoading()
        loadOther()
        loadUI()

        if (checkNetwork()) {
            if (NetUtil.isNetConnect()) {
                loadContent()
            } else {
                alertNoNet()
            }
        }
    }

    open protected fun checkNetwork() : Boolean = true

    private fun alertNoNet() {
        val dialog = AlertDialog.Builder(this).
                setCancelable(false).
                setTitle("无网络连接").
                setMessage("请连接到网络后点击重试按钮").
                setPositiveButton("重试") { dialog, _ ->
                    if (NetUtil.isNetConnect()) {
                        loadContent()
                        dialog.dismiss()
                    } else {
                        alertNoNet()
                    }
                }.
                setNegativeButton("退出") { _,_ ->
                    ActivityUtil.getInstance().exitSystem()
                }
        dialog.show()
    }


    abstract fun loadUI()
    abstract fun loadContent()
    abstract fun loadOther()

    private fun initLoading() {
        mLoadingProgressBar = AlertDialog.Builder(this)
                .setCancelable(true)
                .setView(R.layout.layout_loading)
                .setOnCancelListener { onBackPressed() }
                .create()
        mLoadingProgressBar.window?.setBackgroundDrawable(ColorDrawable(ResourceUtil.getColor(android.R.color.transparent)))
    }

    protected fun showLoading() {
        mLoadingProgressBar.show()
    }

    protected fun dismissLoading() {
        if (mLoadingProgressBar.isShowing && ! isDestroyed) {
            mLoadingProgressBar.dismiss()
        }
    }

    fun goto(cls: Class<*>, vararg extras: Pair<String, String>) {
        val intent = Intent(this, cls)
        for (x in extras) {
            intent.putExtra(x.first, x.second)
        }
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityUtil.getInstance().removeActivity(this)
    }
}
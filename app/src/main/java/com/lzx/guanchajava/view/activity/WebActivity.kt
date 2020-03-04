package com.lzx.guanchajava.view.activity

import android.webkit.WebView
import android.webkit.WebViewClient
import com.lzx.guanchajava.R
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity() {
    override fun loadUI() {
        setContentView(R.layout.activity_web)
    }

    override fun loadContent() {
        showLoading()
        web_view.loadUrl(intent!!.getStringExtra("URL"))
        web_view.webViewClient = MyWebClient()
    }

    override fun loadOther() {
    }

    inner class MyWebClient : WebViewClient() {
        override fun onPageFinished(p0: WebView?, p1: String?) {
            super.onPageFinished(p0, p1)
            dismissLoading()
        }
    }
}

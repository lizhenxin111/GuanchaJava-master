package com.lzx.guanchajava.util

import android.content.Context
import cn.droidlover.xrichtext.XRichText
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.view.activity.ImageActivity
import com.lzx.guanchajava.view.activity.NewsActivity
import org.jetbrains.anko.browse
import org.jetbrains.anko.startActivity

class RichTextUtis(val context: Context) {
    fun showText(view: XRichText, content: String) {
        view.callback(mOnTextClickCallback)
                .text(content)
    }

    private val mOnTextClickCallback = object : XRichText.Callback {
        override fun onImageClick(urlList: MutableList<String>?, position: Int) {
            JumpUtil.startActivity(context, ImageActivity::class.java, TAG.ACTIVITY_IMAGE to urlList!![position])
        }

        override fun onFix(holder: XRichText.ImageHolder?) {
        }

        override fun onLinkClick(url: String?): Boolean {
            if (url!!.contains("guancha.cn")) {
                val id = url.substring(url.lastIndexOf("_")+1, url.lastIndexOf("."))
                App.context.startActivity<NewsActivity>("ID" to id, "CODETYPE" to "2")
            }  else {
                App.context.browse(url)
                //JumpUtil.startActivity(this@ContentWebView.context, WebActivity::class.java, "URL" to url)
            }
            return true
        }

    }
}
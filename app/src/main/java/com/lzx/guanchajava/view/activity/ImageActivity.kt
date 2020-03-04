package com.lzx.guanchajava.view.activity

import android.view.View
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.ImageUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_image.*
import org.jetbrains.anko.sdk27.coroutines.onClick


class ImageActivity : BaseActivity() {

    private lateinit var imageUrl: String

    override fun loadUI() {
        setContentView(R.layout.activity_image)
        registerClickListener()
    }

    override fun loadContent() {
        Logger.d(imageUrl)
        image_view.apply {
            showAlways = true
            url = imageUrl
            onClick { toggleToolbarState() }
        }
    }

    override fun loadOther() {
        val orignalUrl = intent.getStringExtra(TAG.ACTIVITY_IMAGE)
        val type = getImageType(orignalUrl)
        imageUrl = orignalUrl.substring(0, orignalUrl.indexOf(type)+3)
    }

    private fun toggleToolbarState() {
        image_toolbar.visibility = if (image_toolbar.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    private fun registerClickListener() {
        image_back.onClick { onBackPressed() }
        image_download.onClick {
            runOnUiThread {
                ImageUtils.saveImage(this@ImageActivity, imageUrl)
            }
        }
    }

    private fun getImageType(url: String) : String = when {
        url.contains("gif") -> "gif"
        url.contains("png") -> "png"
        url.contains("jpeg") -> "jpeg"
        else -> "jpg"
    }
}

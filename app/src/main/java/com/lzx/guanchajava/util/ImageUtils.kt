package com.lzx.guanchajava.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lzx.guanchajava.R
import com.orhanobut.logger.Logger
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import java.io.File

object ImageUtils {


    fun saveImageWithPopup(url: String, ancher: View) {
        val popup = PopupMenu(ancher.context, ancher, Gravity.BOTTOM)
        popup.menuInflater.inflate(R.menu.menu_save, popup.menu)
        popup.setOnMenuItemClickListener {
            saveImage(ancher.context, url)
            popup.dismiss()
            true
        }
        popup.show()
    }

    fun saveImage(context: Context, url: String) {
        Logger.d(url)


        Glide.with(context)
                .downloadOnly()
                .load(url)
                .listener(object : RequestListener<File> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<File>?, isFirstResource: Boolean): Boolean {
                        context.toast("保存失败，请重试")
                        return false
                    }

                    override fun onResourceReady(resource: File?, model: Any?, target: Target<File>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        val imageDir = File(Environment.getExternalStorageDirectory(), "观察")
                        if (!imageDir.exists()) imageDir.mkdirs()

                        val imageName = "${System.currentTimeMillis()}.${getImageType(url)}"
                        val imagePath = "$imageDir/$imageName"
                        val imageFile = File(imagePath)
                        imageFile.createNewFile()
                        try {
                            val fos = imageFile.outputStream()
                            fos.write(resource!!.readBytes())
                            fos.flush()
                            fos.close()
                            context.longToast("保存到: $imagePath")
                        } catch (e: Exception) {
                            context.toast("保存失败，请重试")
                        }
                        notifyScanGally(context, imagePath)
                        return false
                    }
                })
                .preload()
    }

    private fun notifyScanGally(context: Context, imagePath: String) {
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).apply {
            data = Uri.fromFile(File(imagePath))
        }
        context.sendBroadcast(intent)
    }

    private fun getImageType(url: String) : String = when {
        url.contains("gif") -> "gif"
        url.contains("png") -> "png"
        url.contains("jpeg") -> "jpeg"
        else -> "jpg"
    }
}
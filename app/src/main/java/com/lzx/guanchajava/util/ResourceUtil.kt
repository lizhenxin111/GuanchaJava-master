package com.lzx.guanchajava.util

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import java.io.File
import kotlin.concurrent.thread

/**
 * 获取Resource
 */
object ResourceUtil {
    fun getColor(id: Int) : Int = ContextCompat.getColor(App.context, id)
    fun getString(id: Int) : String = App.context.getString(id)

    fun getAppCacheSize() : Long {
        return getFileSize(App.context.cacheDir.path) +
                getFileSize("/data/data/${App.context.packageName}/databases") +
                getFileSize("/data/data/${App.context.packageName}/shared_prefs") +
                getFileSize("/data/data/${App.context.packageName}/cache")
    }

    fun getFileSize(filePath: String) : Long {
        if (filePath.isEmpty()) return 0L
        val file = File(filePath)
        if (file.isFile) return file.length()
        else if (file.isDirectory){
            var size = 0L
            for (x in file.listFiles())
                size += getFileSize(x.absolutePath)
            return size
        } else {
            return 0L
        }
    }

    fun clearAppCache() {
        deleteFile(App.context.cacheDir.path)       //app运行缓存
        deleteFile("/data/data/${App.context.packageName}/databases")       //数据库
        deleteFile("/data/data/${App.context.packageName}/shared_prefs")        //preferences
        deleteFile("/data/data/${App.context.packageName}/cache")       //各种缓存
        Glide.get(App.context).clearMemory()
        thread {
            Glide.get(App.context).clearDiskCache()
        }
    }

    fun deleteFile(filePath: String) {
        if (filePath.isEmpty()) return
        val file = File(filePath)
        if (file.isDirectory) {
            for (x in file.listFiles())
                deleteFile(x.absolutePath)
            file.delete()
        } else if (file.isFile){
            file.delete()
        }
    }
}
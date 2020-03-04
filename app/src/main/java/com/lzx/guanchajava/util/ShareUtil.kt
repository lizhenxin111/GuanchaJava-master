package com.lzx.guanchajava.util

import cn.sharesdk.evernote.Evernote
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.wechat.favorite.WechatFavorite
import cn.sharesdk.wechat.friends.Wechat
import cn.sharesdk.wechat.moments.WechatMoments
import cn.sharesdk.youdao.YouDao
import com.lzx.guanchajava.POJO.bean.share.Data
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.util.*


object ShareUtil {


    fun shareTo(data: Data, platform: String) {
        val p = ShareSDK.getPlatform(platform)
        p.platformActionListener = shareCallbckListener
        val sp = shareParams(data, platform)
        p.share(sp)
    }


    private fun shareParams(data: Data, platform: String) = Platform.ShareParams().apply {
        /*text = data.summary
        lcDisplayName = data.title
        lcImage = imageObject(data.pic)
        lcUrl = data.url
        lcSummary = data.summary
        lcObjectType = "webpage"*/


        if (platform.equals(Wechat.NAME) || platform.equals(WechatMoments.NAME) || platform.equals(WechatFavorite.NAME)) {
            shareType = Platform.SHARE_WEBPAGE
            title = data.title
            text = data.summary
            url = data.url
            imageUrl = data.pic
        }

        if (platform.equals(YouDao.NAME)) {
            title = data.title
            notebook = data.summary
            text = data.summary
            url = data.url
            imageUrl = data.pic
        }

        if (platform.equals(Evernote.NAME)) {
            title = data.title
            notebook = data.summary
            text = data.summary
            imageUrl = data.pic

        }

        if (platform.equals(QQ.NAME)) {
            title = data.title
            titleUrl = data.url
            text = data.summary
            imageUrl = data.pic
        }
    }

    private fun imageObject(url: String) = JSONObject().apply {
        put("url", url)
        put("width", 120)
        put("height", 120)
    }

    private val shareCallbckListener = object : PlatformActionListener {
        override fun onComplete(p0: Platform?, p1: Int, p2: HashMap<String, Any>?) {
            App.context.toast("分享成功")
        }

        override fun onCancel(p0: Platform?, p1: Int) {
            App.context.toast("取消分享")
        }

        override fun onError(p0: Platform?, p1: Int, p2: Throwable?) {
            App.context.toast("分享失败: ${p2?.message}")
        }

    }
}

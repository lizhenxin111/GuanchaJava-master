package com.lzx.guanchajava.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.notice.NoticeCount
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.UserInfoActivity
import com.lzx.guanchajava.view.activity.UserInfoTag
import com.lzx.guanchajava.util.NotificationUtil
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.longToast

class CheckNoticeService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Fuel.get(Api.noticeCount()).responseString { _, response, result ->
            val(data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val data = Gson().fromJson(result.get(), NoticeCount::class.java).data

                noticeNotice(data.reply_count, data.collection_praise_count, data.vote_count, data.attention_count)

                noticeMessage(6, data.message_count)

                noticeSubsribe(7, data.subscription_count)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 通知
     *
     */
    private fun noticeNotice(repliedNum: Int, praisedNum: Int, votedNum: Int, attentionNum: Int) {

        var hasNotice = false

        var noticeContent = "您有"
        if (repliedNum != 0) {
            noticeContent += "${repliedNum}条新回复"
            hasNotice = true
        }
        if (praisedNum != 0) {
            noticeContent += "${praisedNum}条点赞"
            hasNotice = true
        }
        if (votedNum != 0) {
            noticeContent += "${votedNum}个投票"
            hasNotice = true
        }
        if (attentionNum != 0) {
            noticeContent += "${attentionNum}个关注"
            hasNotice = true
        }

        if (hasNotice) {
            val intent = Intent(this, UserInfoActivity::class.java).apply {
                putExtra(UserInfoTag.TAG, UserInfoTag.TAG_NOTICE)
            }
            val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            NotificationUtil.showNotification(1, "通知", "通知", noticeContent, pendingIntent)
        }
    }

    /**
     * 订阅
     */
    private fun noticeSubsribe(id: Int, count: Int) {
        if (count != 0) {
            val intent = Intent(this, UserInfoActivity::class.java).apply {
                putExtra(UserInfoTag.TAG, UserInfoTag.TAG_SUBSCRIBE)
            }
            val pendingIntent = PendingIntent.getActivity(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            NotificationUtil.showNotification(id,"通知", "观察者网", "您的订阅有${count}条更新", pendingIntent)
        }
    }

    /**
     * 私信
     */
    private fun noticeMessage(id: Int, count: Int) {
        if (count != 0) {
            val intent = Intent(this, UserInfoActivity::class.java).apply {
                putExtra(UserInfoTag.TAG, UserInfoTag.TAG_MESSAGE)
            }
            val pendingIntent = PendingIntent.getActivity(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            NotificationUtil.showNotification(id,"通知", "观察者网", "您有${count}条私信", pendingIntent)
        }
    }
}
package com.lzx.guanchajava.view.fragment.share

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cn.sharesdk.sina.weibo.SinaWeibo
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.wechat.favorite.WechatFavorite
import cn.sharesdk.wechat.friends.Wechat
import cn.sharesdk.wechat.moments.WechatMoments
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.share.ShareInfo
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.share.ShareAdapter
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.util.ResourceUtil
import com.lzx.guanchajava.util.ShareUtil
import com.lzx.guanchajava.view.widget.EmptyRecyclerView
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.toast

class ShareFragment : BottomSheetDialogFragment() {


    private val mNameList = listOf(SinaWeibo.NAME, Wechat.NAME, WechatMoments.NAME, WechatFavorite.NAME, QQ.NAME)
    private val mTitleList = listOf("微博", "微信好友", "朋友圈", "微信收藏", "QQ", "复制链接")


    companion object {
        fun newInstance(id: String, type: String) = ShareFragment().apply {
            arguments = Bundle().apply {
                putString("ID", id)
                putString("TYPE", type)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_share, container, false)
        val list = v.find<EmptyRecyclerView>(R.id.share_list)
        v.find<MaterialButton>(R.id.share_cancel).onClick { dismiss() }

        val picList = listOf(R.drawable.ic_share_sina,
                R.drawable.ic_share_wechat,
                R.drawable.ic_share_moment,
                R.drawable.ic_share_wechat_favorite,
                R.drawable.ic_share_qq,/*
                R.drawable.ic_share_evernote,
                R.drawable.ic_share_youdao,*/
                R.drawable.ic_share_link)

        list.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = ShareAdapter(picList, mTitleList, onClickListener)
        }
        return v
    }

    private val onClickListener = object : ShareAdapter.OnClickListener {
        override fun onClick(position: Int) {
            Fuel.post(Api.shareInfo(arguments!!.getString("ID"), arguments!!.getString("TYPE"))).responseString { request, response, result ->
                val (data, error) = result
                if (error != null) {
                    longToast(ResourceUtil.getString(R.string.str_no_network))
                } else {
                    val data = Gson().fromJson(result.get(), ShareInfo::class.java).data
                    //ShareUtil.oneKeyShare(data)
                    if (position == 5) {
                        copyToClipboard(data.url)
                    } else ShareUtil.shareTo(data, mNameList[position])
                    dismiss()
                }
            }
        }
    }

    private fun copyToClipboard(url: String) {
        val clipboardManager = App.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("LABEL_COPY_LINK", url)
        clipboardManager.primaryClip = clipData
        toast("已复制链接到剪贴板")
    }

}
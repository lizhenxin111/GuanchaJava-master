package com.lzx.guanchajava.util

import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.fragment.app.FragmentManager
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.operate.praiseComment.PraiseComment
import com.lzx.guanchajava.POJO.bean.operate.treadComment.TreadComment
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.fragment.reply.ChildReplyFragment
import com.lzx.guanchajava.view.fragment.reply.CommentFragment
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

object CommentUtil {

    fun publishComment(parentId : String, type : String, codeId : String, name : String, fragmentManager: FragmentManager) {
        CommentFragment.newInstance(type, codeId, parentId, name).show(fragmentManager, "COMMENT")
    }

    fun praiseComment(commentId: String, btn: TextView) {
        if (!LoginUtil.hasToken()) {
            App.context.toast("请登录")
            return
        }
        Fuel.post(Api.praiseComment(), listOf("comment_id" to commentId, "from" to "cms")).responseObject(PraiseComment.Deserializer()) { _, response, result ->
            val(data, error) = result
            if (error != null) {
                App.context.longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val r = result.get().data.is_praise
                if (r) {
                    Toast.makeText(App.context, "赞一下", Toast.LENGTH_SHORT).show()
                    setImageDrawable(btn, R.drawable.ic_praise_fill_12dp)
                    increaseNum(btn)
                } else {
                    Toast.makeText(App.context, "不赞了", Toast.LENGTH_SHORT).show()
                    setImageDrawable(btn, R.drawable.ic_praise_12dp)
                    decreaseNum(btn)
                }
            }
        }
    }

    fun treadComment(commentId: String, btn: TextView) {
        if (!LoginUtil.hasToken()) {
            App.context.toast("请登录")
            return
        }
        Fuel.post(Api.treadComment(), listOf("id" to commentId, "from" to "cms")).responseObject(TreadComment.Deserializer()) { request, response, result ->
            val(data, error) = result
            if (error != null) {
                App.context.longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val r = result.get().data.is_tread
                if (r) {
                    Toast.makeText(App.context, "踩一下", Toast.LENGTH_SHORT).show()
                    setImageDrawable(btn, R.drawable.ic_tread_fill_12dp)
                    increaseNum(btn)
                } else {
                    Toast.makeText(App.context, "不踩了", Toast.LENGTH_SHORT).show()
                    setImageDrawable(btn, R.drawable.ic_tread_12dp)
                    decreaseNum(btn)
                }
            }
        }
    }

    fun setImageDrawable(btn: TextView, @DrawableRes id: Int) {
        val b = App.context.getDrawable(id)
        btn.setCompoundDrawablesWithIntrinsicBounds(b, null, null, null)
    }

    private fun increaseNum(num: TextView) {
        val n = num.text.toString().toInt()
        num.text = (n + 1).toString()
    }

    private fun decreaseNum(num: TextView) {
        val n = num.text.toString().toInt()
        num.text = (n - 1).toString()
    }

    fun showChild(childId: String, fragmentManager: FragmentManager, title: String) {
        ChildReplyFragment.newInstance(childId, title).show(fragmentManager, "CHILDREPLY")
    }

}
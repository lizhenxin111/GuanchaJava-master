package com.lzx.guanchajava.member.activity

import android.content.Intent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.BaseActivity
import com.lzx.guanchajava.view.activity.LoginActivity
import com.lzx.guanchajava.util.JumpUtil
import com.lzx.guanchajava.util.LoginUtil
import kotlinx.android.synthetic.main.activity_member.*
import kotlinx.android.synthetic.main.content_member.*

class MemberActivity : BaseActivity() {

    override fun loadUI() {
        setContentView(R.layout.activity_member)
    }

    override fun loadContent() {
        toolbar.title = "特异功能"
        if (checkLogin()) {
            loadItemInfo()
        } else {
            Snackbar.make(member_container, "请登录", Snackbar.LENGTH_INDEFINITE).setAction("登录") {
                val intent = Intent(this, LoginActivity::class.java)
                startActivityForResult(intent, com.lzx.guanchajava.POJO.TAG.CODE_LOGIN_REQUEST)
            }.show()
        }
    }

    override fun loadOther() {
    }

    private fun checkLogin() = LoginUtil.hasToken()


    /**
     * 会员每个项目的详情，比如收藏的评论的数量、点击跳转
     */
    private fun loadItemInfo() {
        member_function_layout.visibility = View.VISIBLE
        member_future_layout.visibility = View.VISIBLE

        member_comment_collect.apply {
            setOnClickListener(onClickListener)
        }
        member_note.apply {
            setOnClickListener(onClickListener)
        }
        member_block_user.apply {
            setOnClickListener(onClickListener)
        }
    }

    private val onClickListener = View.OnClickListener { v ->
        val tag : String = when(v.id) {
            R.id.member_comment_collect -> MEMBER_ITEM_CC
            R.id.member_note -> MEMBER_ITEM_NOTE
            R.id.member_block_user -> MEMBER_ITEM_BLOCK
            else -> ""
        }
        JumpUtil.startActivity(this@MemberActivity, MemberItemActivity::class.java, MEMBER_ITEM_TAG to tag)
    }
}

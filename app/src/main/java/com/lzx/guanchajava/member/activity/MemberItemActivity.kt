package com.lzx.guanchajava.member.activity

import androidx.fragment.app.Fragment
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.BaseActivity
import com.lzx.guanchajava.member.fragment.ArticleListFragment
import com.lzx.guanchajava.member.fragment.CommentCollectedFragment
import com.lzx.guanchajava.member.fragment.HardBlackFragment
import kotlinx.android.synthetic.main.activity_member_item.*

const val MEMBER_ITEM_TAG = "_MEMBER_ITEM_TAG_"
const val MEMBER_ITEM_CC = "_MEMBER_ITEM_CC_"      //comment collection
const val MEMBER_ITEM_NOTE = "_MEMBER_ITEM_NOTE_"      //comment collection
const val MEMBER_ITEM_BLOCK = "_MEMBER_ITEM_BLOCK_"      //comment collection
class MemberItemActivity : BaseActivity() {

    private lateinit var TAG : String
    override fun loadUI() {
        setContentView(R.layout.activity_member_item)
    }

    override fun loadContent() {
        TAG = intent.getStringExtra(MEMBER_ITEM_TAG)
        val fragment = searchFragment(TAG)

        showFragment(fragment)
        setTitle(TAG)
    }

    override fun loadOther() {
    }

    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.member_item_container, fragment).commit()
    }

    private fun setTitle(tag: String) {
        toolbar.title = selectTitle(tag)
    }

    private fun searchFragment(tag: String) = when(tag) {
        MEMBER_ITEM_CC -> CommentCollectedFragment.newInstance(true)
        MEMBER_ITEM_NOTE -> ArticleListFragment.newInstance(true)
        MEMBER_ITEM_BLOCK -> HardBlackFragment.newInstance(true)
        else -> Fragment()
    }

    private fun selectTitle(tag: String) = when(TAG) {
        MEMBER_ITEM_CC -> "收藏的评论"
        MEMBER_ITEM_NOTE -> "新闻笔记"
        MEMBER_ITEM_BLOCK -> "超级黑名单"
        else -> ""
    }
}

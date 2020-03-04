package com.lzx.guanchajava.view.activity

import android.content.Intent
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.fragment.userDetail.UserPublishFragment
import com.lzx.guanchajava.view.fragment.userDetail.collection.UserCollectionFragment
import com.lzx.guanchajava.view.fragment.userInfo.FansFragment
import com.lzx.guanchajava.view.fragment.userInfo.UserMessageFragment
import com.lzx.guanchajava.view.fragment.userInfo.UserSubscriptionFragment
import com.lzx.guanchajava.view.fragment.userInfo.history.UserHistoryFragment
import com.lzx.guanchajava.view.fragment.userInfo.notice.NoticeFragment
import com.lzx.guanchajava.util.LoginUtil
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.content_user_info.*

class UserInfoActivity : BaseActivity() {

    private var UID: String? = null

    override fun loadUI() {
        setContentView(R.layout.activity_user_info)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun loadContent() {
        if (LoginUtil.hasToken()) {
            loadFragment()
        } else {
            Snackbar.make(user_info_container, "请登录", Snackbar.LENGTH_INDEFINITE).setAction("登录") {
                val intent = Intent(this, LoginActivity::class.java)
                startActivityForResult(intent, TAG.CODE_LOGIN_REQUEST)
            }.show()
        }
    }

    override fun loadOther() {
        UID = intent?.getStringExtra("UID")
    }


    private fun loadFragment() {
        val tag = intent.getIntExtra(UserInfoTag.TAG, -1)
        title = getTitle(tag)
        val fragment = getFragment(tag)
        supportFragmentManager.beginTransaction().replace(R.id.user_info_container, fragment).commit()
    }

    private fun getFragment(tag: Int) : Fragment {
        val uid = UID?:LoginUtil.getUid().toString()
        var fragment = Fragment()
        when(tag) {
            UserInfoTag.TAG_PROFILE -> fragment = Fragment()

            UserInfoTag.TAG_PUBLISH -> {
                val replyNum = intent!!.getIntExtra(TAG.USER_INFO_REPLY, 0)
                val articleNum = intent!!.getIntExtra(TAG.USER_INFO_ARTICLE, 0)
                val praiseNum = intent!!.getIntExtra(TAG.USER_INFO_PRAISE, 0)
                fragment = UserPublishFragment.newInstance(replyNum, articleNum, praiseNum)
            }

            UserInfoTag.TAG_ATTENTION -> fragment = FansFragment.newInstance(Api.userAttention(uid), true)

            UserInfoTag.TAG_FANS -> fragment = FansFragment.newInstance(Api.userFans(uid), true)

            UserInfoTag.TAG_COLLECTION -> fragment = UserCollectionFragment.newInstance(Api.userCollect(uid), true)/*CollectionNavFragment.newInstance(uid)*/

            UserInfoTag.TAG_NOTICE -> {
                fragment = NoticeFragment()
            }

            UserInfoTag.TAG_SUBSCRIBE -> fragment = UserSubscriptionFragment.newInstance(Api.subscription(), true)

            UserInfoTag.TAG_MESSAGE -> fragment = UserMessageFragment.newInstance(true)

            UserInfoTag.TAG_FEEDBACK -> {

            }

            UserInfoTag.TAG_HISTORY -> fragment = UserHistoryFragment()
            else -> {
                fragment = FansFragment.newInstance(Api.blackList(), true)
            }
        }
        return fragment
    }

    private fun getTitle(tag: Int) : String {
        when(tag) {
            UserInfoTag.TAG_PROFILE -> return "详细信息"
            UserInfoTag.TAG_PUBLISH -> return "发表"
            UserInfoTag.TAG_ATTENTION -> return "关注"
            UserInfoTag.TAG_FANS -> return "粉丝"
            UserInfoTag.TAG_COLLECTION -> return "收藏"
            UserInfoTag.TAG_NOTICE -> return "通知"
            UserInfoTag.TAG_SUBSCRIBE -> return "订阅"
            UserInfoTag.TAG_MESSAGE -> return "私信"
            UserInfoTag.TAG_FEEDBACK -> return "反馈"
        }
        return "黑名单"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TAG.CODE_LOGIN_REQUEST && resultCode == TAG.CODE_LOGIN_RESULT) loadFragment()
    }
}

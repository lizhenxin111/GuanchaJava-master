package com.lzx.guanchajava.view.activity

import android.view.Menu
import android.view.MenuItem
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.black.Black
import com.lzx.guanchajava.POJO.bean.operate.attendUser.AttendUser
import com.lzx.guanchajava.POJO.bean.userDetail.profile.UserDetail
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.FragmentsAdapter
import com.lzx.guanchajava.view.fragment.userDetail.UserArticleFragment
import com.lzx.guanchajava.view.fragment.userDetail.UserPraiseFragment
import com.lzx.guanchajava.view.fragment.userDetail.UserReplyFragment
import com.lzx.guanchajava.view.fragment.userDetail.collection.UserCollectionFragment
import com.lzx.guanchajava.member.bean.DBHardBlack
import com.lzx.guanchajava.member.db.HardBlackDB
import com.lzx.guanchajava.util.ResourceUtil
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.content_user_detail.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 用户详情
 */
class UserDetailActivity : BaseActivity() {

    private lateinit var UID: String
    private lateinit var NAME: String
    private lateinit var PIC: String
    private val tabs = listOf("回复", "文章", "点赞", "收藏")

    private var hasBlack = false
    private var isHardBlack = false

    override fun loadUI() {
        setContentView(R.layout.activity_user_detail)
        setSupportActionBar(toolbar)

        for (x in 0..3) {
            user_tabs.addTab(user_tabs.newTab())
            user_tabs.getTabAt(x)?.text = tabs[x]
        }

        user_pager.offscreenPageLimit = 3

        user_tabs.tabMode = TabLayout.MODE_FIXED
        user_tabs.setupWithViewPager(user_pager)

        header_attention.onClick { startActivity<UserInfoActivity>(UserInfoTag.TAG to UserInfoTag.TAG_ATTENTION, "UID" to UID) }
        header_fans.onClick { startActivity<UserInfoActivity>(UserInfoTag.TAG to UserInfoTag.TAG_FANS, "UID" to UID) }

        header_follow.onClick { followUser() }
    }

    override fun loadContent() {
        val pagerAdapter = FragmentsAdapter(supportFragmentManager).apply {
            reGetTitle = true
            fragments = listOf(UserReplyFragment.newInstance(Api.userComment(UID)),
                    UserArticleFragment.newInstance(Api.userPublish(UID)),
                    UserPraiseFragment.newInstance(Api.userPraise(UID)),
                    UserCollectionFragment.newInstance(Api.userCollect(UID)))
            pageTitles = tabs
        }

        user_pager.adapter = pagerAdapter
        loadProfile()
    }

    override fun loadOther() {
        UID = intent.getStringExtra("UID")
    }


    private fun loadProfile() {
        Fuel.post(Api.userInfo(), listOf("uid" to UID)).responseObject(UserDetail.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val data = result.get().data
                NAME = data.user_nick
                PIC = data.user_photo
                user_pic.url = data.user_photo
                header_attention.text = "关注: ${data.attention_nums}"
                header_fans.text = "粉丝: ${data.fans_nums}"
                toolbar_layout?.title = data.user_nick
                if (data.has_attention) header_follow.text = "已关注"
                if (data.has_black) {
                    hasBlack = true
                    invalidateOptionsMenu()
                }
            }
        }
    }

    private fun blackUser() {
        Fuel.post(Api.black(), listOf("touid" to UID)).responseString { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val data = Gson().fromJson(result.get(), Black::class.java)
                if (data.data.action.equals("black")) toast("已加入黑名单")
                else toast("已移除黑名单")
                hasBlack = !hasBlack
            }
        }
    }

    private fun hardBlackUser() {
        blackUser()
        isHardBlack = !isHardBlack
        val user = DBHardBlack(UID, NAME, PIC, isHardBlack)
        if (HardBlackDB.checkExit(user)) {
            HardBlackDB.deleteDB(user)
            Logger.d("delete")
        } else {
            HardBlackDB.saveUser(user)
            Logger.d("save")
        }
    }

    private fun gotoMessage() {
        startActivity<UserMessageActivity>("UID" to UID, "NAME" to NAME)
    }

    private fun followUser() {
        Fuel.post(Api.attentionUser(), listOf("touid" to UID)).responseString { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val data = Gson().fromJson(result.get().replace("[]", "{}"), AttendUser::class.java)
                if (data.code == 0) {
                    if (header_follow.text.equals("关注")) header_follow.text = "已关注"
                    else header_follow.text = "关注"
                } else toast("操作失败: ${data.msg}")
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isHardBlack) menuInflater.inflate(R.menu.menu_user_detail_hard_black, menu)
        else {
            if (hasBlack) menuInflater.inflate(R.menu.menu_user_detail_black, menu)
            else menuInflater.inflate(R.menu.menu_user_detail, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_pl -> {
                gotoMessage()
                true
            }
            android.R.id.home -> {
                this.onBackPressed()
                true
            }
            R.id.action_black -> {
                blackUser()
                true
            }
            R.id.action_hard_black -> {
                hardBlackUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

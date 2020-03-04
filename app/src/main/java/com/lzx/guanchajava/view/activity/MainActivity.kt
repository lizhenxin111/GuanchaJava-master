package com.lzx.guanchajava.view.activity

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.lzx.guanchajava.POJO.ACTION_CHANGE_SHOW_MODE
import com.lzx.guanchajava.POJO.ACTION_LOGIN_IN
import com.lzx.guanchajava.POJO.ACTION_LOG_OUT
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.userInfo.tokenValid.TokenState
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.FragmentsAdapter
import com.lzx.guanchajava.view.fragment.bottomNavFragments.AuthorFragment
import com.lzx.guanchajava.view.fragment.bottomNavFragments.NewsNavFragments
import com.lzx.guanchajava.view.fragment.bottomNavFragments.TopicNavFragments
import com.lzx.guanchajava.member.activity.MemberActivity
import com.lzx.guanchajava.service.CheckNoticeService
import com.lzx.guanchajava.util.*
import com.lzx.guanchajava.view.widget.InfoDialog
import com.lzx.guanchajava.view.widget.UrlImageView
import com.mob.MobSDK
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, EasyPermissions.PermissionCallbacks {

    private var hasCheckToken = false

    private var mLocalReceiver = LocalReceiver()

    private var exitTime: Long = 0

    private lateinit var pic: UrlImageView
    private lateinit var nick: TextView
    private lateinit var des: TextView
    private lateinit var attention: TextView
    private lateinit var fans: TextView
    private lateinit var publish: TextView
    private lateinit var headerClick: TextView
    private lateinit var headerInfo: ConstraintLayout


    override fun loadUI() {
        initFrame()
        initProfile()
    }

    override fun loadContent() {
        if (SpUtil.agreeTerms()) {
            initContent()
        } else {
            val dialog = InfoDialog()
            dialog.onDialogButtonClick = object : InfoDialog.OnDialogButtonClick {
                override fun onPositiveClick(dialog: InfoDialog) {
                    dialog.dismiss(this@MainActivity)
                    SpUtil.setAgreeTerms(true)
                    initContent()
                }

                override fun onNegativeClick(dialog: InfoDialog) {
                    dialog.dismiss(this@MainActivity)
                    ActivityUtil.getInstance().exitSystem()
                }

            }
            dialog.show(this)
        }

    }

    override fun loadOther() {
        ReceiverUtil.registerLoginReceiver(mLocalReceiver)
        ReceiverUtil.registerLogoutReceiver(mLocalReceiver)
        ReceiverUtil.registerShowModeReceiver(mLocalReceiver)

        setShowMode(false)

        doAsync {
            Beta.autoCheckUpgrade = SettingUtil.isAutoCheckUpdate()
            Beta.autoInstallApk = true
            Beta.initDelay = 4000
            Logger.addLogAdapter(AndroidLogAdapter())
            MobSDK.init(this@MainActivity)
        }

        if (!EasyPermissions.hasPermissions(this, Manifest.permission_group.STORAGE))
            EasyPermissions.requestPermissions(this, "需要您的存储权限以保存图片", 1, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onResume() {
        super.onResume()
        if (SettingUtil.isReceiveNotice()) checkNotice()
    }

    /**
     * 初始化框架UI，包括Drawer+BottomSheet+Toolbar
     */
    private fun initFrame() {
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        main_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        main_navigation.labelVisibilityMode


        main_pager.canScroll = false
        main_pager.offscreenPageLimit = 2   //当前位置左右两侧保留的fragment数量

    }

    /**
     * 初始化Drawer中个人信息部分的UI
     */
    private fun initProfile() {
        val headerView = nav_view.getHeaderView(0)
        pic = headerView.find(R.id.header_pic)
        pic.ifCircle = true
        nick = headerView.find(R.id.header_nick)
        des = headerView.find(R.id.header_des)
        attention = headerView.find(R.id.header_follow)
        fans = headerView.find(R.id.header_fans)
        publish = headerView.find(R.id.header_publish)
        headerClick = headerView.find(R.id.header_click)
        headerInfo = headerView.find(R.id.header_user_info)

        pic.onClick {
            startActivity<UserProfileActivity>()
        }
        attention.setOnClickListener(profileClickObj)
        fans.setOnClickListener(profileClickObj)
        publish.setOnClickListener(profileClickObj)

        headerClick.setOnClickListener(profileClickObj)
    }

    private fun initContent() {
        checkTokenState()       //登录

        //内部fragment
        main_pager.adapter = FragmentsAdapter(supportFragmentManager).apply {
            fragments = listOf(NewsNavFragments.newsInstance(), TopicNavFragments.newsInstance(), AuthorFragment())
        }
    }

    /**
     * Drawer中个人信息点击事件
     */
    private val profileClickObj = View.OnClickListener { v ->
        when (v!!.id) {
            R.id.header_click -> startActivity<LoginActivity>()

            R.id.header_pic -> startActivity<UserProfileActivity>()
            R.id.header_nick -> startActivity<UserProfileActivity>()
            R.id.header_des -> startActivity<UserProfileActivity>()
            R.id.header_publish -> startActivity<UserInfoActivity>(UserInfoTag.TAG to UserInfoTag.TAG_PUBLISH)
            R.id.header_follow -> startActivity<UserInfoActivity>(UserInfoTag.TAG to UserInfoTag.TAG_ATTENTION)
            R.id.header_fans -> startActivity<UserInfoActivity>(UserInfoTag.TAG to UserInfoTag.TAG_FANS)
        }
    }

    /**
     * 检查通知
     */
    private fun checkNotice() {
        if (hasCheckToken && LoginUtil.hasToken())
            startService(Intent(this, CheckNoticeService::class.java))
    }

    /**
     * 本地是否有token
     */
    private fun hasToken(): Boolean {
        return LoginUtil.hasToken()
    }

    /**
     * 检查token是否可用
     */
    private fun checkTokenState() {
        if (hasToken()) {
            Fuel.get(Api.isTokenValid()).responseObject(TokenState.Deserializer()) { _, response, result ->
                val (data, error) = result
                if (error != null) {
                    longToast(ResourceUtil.getString(R.string.str_no_network))
                } else {
                    val state = result.get().data.is_valid
                    Log.d("TOKEN", result.get().msg)
                    if (state) {
                        hasCheckToken = true
                        loginFromLocal()
                        checkNotice()
                    } else clearLogin()
                }
            }
        }
    }

    /**
     * 清除登录信息
     */
    private fun clearLogin() {
        headerClick.visibility = View.VISIBLE
        headerInfo.visibility = View.GONE
        LoginUtil.clearLogin()
    }

    /**
     * 以本地信息登录
     */
    private fun loginFromLocal() {
        headerClick.visibility = View.GONE
        headerInfo.visibility = View.VISIBLE
        setUserInfo()
    }


    /**
     * 设置用户信息到drawer中
     */
    private fun setUserInfo() {
        val info = LoginUtil.getInfo()
        pic.url = info.user_photo
        nick.text = info.user_nick
        des.text = info.user_description
        attention.text = "关注：${info.attention_nums}"
        fans.text = "粉丝：${info.fans_nums}"
        publish.text = "发表"
    }

    private fun setShowMode(recreate: Boolean) {
        if (!defaultSharedPreferences.contains(TAG.PREF_NIGHT_MODE)) return

        val isNightMode = defaultSharedPreferences.getBoolean(TAG.PREF_NIGHT_MODE, false)
        if (isNightMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (recreate) recreate()
    }


    /**
     * Drawer中点击事件
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_collection -> startActivity<UserInfoActivity>(UserInfoTag.TAG to UserInfoTag.TAG_COLLECTION)
            R.id.nav_notice -> startActivity<UserInfoActivity>(UserInfoTag.TAG to UserInfoTag.TAG_NOTICE)
            R.id.nav_subscribtion -> startActivity<UserInfoActivity>(UserInfoTag.TAG to UserInfoTag.TAG_SUBSCRIBE)
            R.id.nav_massage -> startActivity<UserInfoActivity>(UserInfoTag.TAG to UserInfoTag.TAG_MESSAGE)
            R.id.nav_feedback -> {
                startActivity<FeedbackActivity>()
            }
            R.id.nav_setting -> startActivity<SettingsActivity>()
            R.id.nav_history -> startActivity<UserInfoActivity>(UserInfoTag.TAG to UserInfoTag.TAG_HISTORY)
            R.id.nav_member -> startActivity<MemberActivity>()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * 下方导航栏点击事件
     */
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_news -> {
                main_pager.currentItem = 0
                supportActionBar?.title = "新闻"
                invalidateOptionsMenu()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_topic -> {
                main_pager.currentItem = 1
                supportActionBar?.title = "风闻"
                invalidateOptionsMenu()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_author -> {
                main_pager.currentItem = 2
                supportActionBar?.title = "专栏"
                invalidateOptionsMenu()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        when {
            main_pager.currentItem == 1 -> menuInflater.inflate(R.menu.menu_post, menu)
            main_pager.currentItem == 0 -> menuInflater.inflate(R.menu.menu_search, menu)
            main_pager.currentItem == 2 -> menuInflater.inflate(R.menu.menu_search, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.menu_post -> {
                goto(PostArticleActivity::class.java)
                true
            }
            R.id.menu_search -> {
                goto(SearchActivity::class.java)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // 监听返回键，点击两次退出程序
        if (keyCode == KEYCODE_BACK && event.action === KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 1500) {
                Toast.makeText(applicationContext, "再按一次退出程序", Toast.LENGTH_LONG).show()
                exitTime = System.currentTimeMillis()
            } else {
                ActivityUtil.getInstance().exitSystem()
                //finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        ReceiverUtil.unregisterLoginReceiver(mLocalReceiver)
        ReceiverUtil.unregisterLogoutReceiver(mLocalReceiver)
        ReceiverUtil.unregisterShowModeReceiver(mLocalReceiver)
        if (LoginUtil.hasToken()) stopService(Intent(this, CheckNoticeService::class.java))
    }

    inner class LocalReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when (intent.action) {
                ACTION_CHANGE_SHOW_MODE -> {
                    setShowMode(true)
                }
                ACTION_LOGIN_IN -> {
                    loginFromLocal()
                }
                ACTION_LOG_OUT -> {
                    clearLogin()
                }
            }
        }
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        toast("未获取存储权限， 无法保存图片")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        //toast("已获取权限")
    }
}

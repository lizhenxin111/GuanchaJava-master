package com.lzx.guanchajava.view.activity

import android.view.View
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.login.Login
import com.lzx.guanchajava.POJO.bean.userDetail.userInfo.UserInfo
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.LoginUtil
import com.lzx.guanchajava.util.ReceiverUtil
import com.lzx.guanchajava.util.ResourceUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast


class LoginActivity : BaseActivity() {
    override fun loadUI() {
        setContentView(R.layout.activity_login)

        //login_phone_edit.addTextChangedListener(phoneChangeWatcherObj)

        login_btn.setOnClickListener(loginClickObj)

        login_register.onClick { loadUrlByBrowser(Api.officalRegister()) }
        login_retrieve.onClick { loadUrlByBrowser(Api.officalRetrieve()) }

    }

    override fun loadContent() {
    }

    override fun loadOther() {
    }


    private val loginClickObj = View.OnClickListener {
        when {
            login_phone_edit.text.isNullOrEmpty() -> Snackbar.make(login_container, "手机号码为空", Snackbar.LENGTH_SHORT)
            login_passwd_edit.text.isNullOrEmpty() -> Snackbar.make(login_container, "密码为空", Snackbar.LENGTH_SHORT)
            else -> login()
        }
    }

    private fun login() {
        showLoading()
        Fuel.post(Api.login(), listOf("username" to login_phone_edit.text, "password" to login_passwd_edit.text, "from" to "3", "phone_code" to "86")).responseString { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                try {
                    val loginResult = Gson().fromJson(result.get(), Login::class.java)
                    if (loginResult.code == 4) {
                        Snackbar.make(login_container, loginResult.msg, Snackbar.LENGTH_SHORT)
                    } else {
                        LoginUtil.saveLogin(loginResult.data.user, loginResult.data.token)
                        toast("登录成功")
                        Fuel.post(Api.userInfo(), listOf("uid" to LoginUtil.getUid())).responseString { request, response, result ->
                            val (data, error) = result
                            if (error != null) {
                                longToast(ResourceUtil.getString(R.string.str_no_network))
                            } else {

                                val data = Gson().fromJson(result.get(), UserInfo::class.java).data
                                LoginUtil.saveInfo(data)
                                ReceiverUtil.sendLoginInBroadcast()
                                dismissLoading()
                                setResult(com.lzx.guanchajava.POJO.TAG.CODE_LOGIN_RESULT)
                                finishAfterTransition()
                            }
                        }
                        //setResult(com.lzx.guanchajava.POJO.TAG.CODE_LOGIN_RESULT)
                    }
                } catch (e: Exception) {
                    longToast("登录失败, 用户名或密码错误")
                    dismissLoading()
                }
            }
        }
    }

    private fun loadUrlByBrowser(url: String) {
        browse(url)
    }
    /*
        private val phoneChangeWatcherObj = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!RegexUtils.checkMobile(s.toString())) {
                    login_ti_phone.isErrorEnabled = true
                    login_ti_phone.error = "手机号码格式错误"
                } else {
                    login_ti_phone.isErrorEnabled = false
                }
            }
        }*/
}
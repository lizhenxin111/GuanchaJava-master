package com.lzx.guanchajava.view.activity

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.edit.Edit
import com.lzx.guanchajava.POJO.bean.userDetail.profile.Data
import com.lzx.guanchajava.POJO.bean.userDetail.profile.UserDetail
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.LoginUtil
import com.lzx.guanchajava.util.ReceiverUtil
import com.lzx.guanchajava.util.ResourceUtil
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.content_user_profile.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * 用户详细信息
 */
class UserProfileActivity : BaseActivity() {
    override fun loadUI() {
        setContentView(R.layout.activity_user_profile)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        user_sign_out.onClick {
            ReceiverUtil.sendLogoutBroadcast()
            onBackPressed()
        }
    }

    override fun loadContent() {
        Fuel.post(Api.userInfo(), listOf("uid" to LoginUtil.getUid())).responseObject(UserDetail.Deserializer()) { request, response, result ->
            val(data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val data = result.get().data
                setData(data)
            }
        }
    }

    override fun loadOther() {
    }


    private lateinit var defaultName: String
    private lateinit var defaultGender: String
    private lateinit var defaultDes: String
    private lateinit var defaultGradute: String
    private lateinit var defaultProfession: String
    private lateinit var defaultBirthday: String


    private fun setData(data: Data) {
        user_pic.apply {
            url = data.user_photo
            ifCircle = true
        }
        user_name.setText(data.user_nick)
        user_gender_group.check(getGender(data.user_sex))
        user_des_text.setText(data.user_description)
        user_graduate_text.setText(data.user_graduate)
        user_profession_text.setText(data.user_profession)
        user_birthdate_text.setText(data.user_birthdate)

        defaultName = data.user_nick
        defaultGender = data.user_sex.toString()
        defaultDes = data.user_description
        defaultGradute = data.user_graduate
        defaultProfession = data.user_profession
        defaultBirthday = data.user_birthdate
    }

    private fun editName() {

        if (defaultName.equals(user_name.text.toString())) {
            editOther()
        } else {
            Fuel.post(Api.editUsername(), listOf("user_nick" to user_name.text.toString())).responseObject(Edit.Deserializer()) { request, response, result ->
                val (data, error) = result
                if (error != null) {
                    longToast(ResourceUtil.getString(R.string.str_no_network))
                } else {
                    val r = result.get()
                    if (r.code == 0) {
                        toast(r.msg)
                        editOther()
                    } else {
                        toast("修改用户名失败, ${r.msg}")
                    }
                }
            }
        }
    }

    private fun editOther() {
        if (!defaultGender.equals(user_gender_group.checkedRadioButtonId.toString()) or
                !defaultDes.equals(user_des_text.text.toString()) or
                !defaultGradute.equals(user_graduate_text.text.toString()) or
                !defaultProfession.equals(user_profession_text.text.toString()) or
                !defaultBirthday.equals(user_birthdate_text.text.toString())) {     //只要有一项为true就修改
            Fuel.post(Api.editOtherInfo(), listOf("user_description" to user_des_text.text.toString(),
                    "user_profession" to user_profession_text.text.toString(),
                    "user_graduate" to user_graduate_text.text.toString(),
                    "user_sex" to getGenderCode(user_gender_group.checkedRadioButtonId))).responseObject(Edit.Deserializer()) { request, response, result ->
                val (data, error) = result
                if (error != null) {
                    longToast(ResourceUtil.getString(R.string.str_no_network))
                } else {
                    val r = result.get()
                    if (r.code == 0) {
                        toast(r.msg)
                        onBackPressed()
                    } else {
                        toast("修改信息失败, ${r.msg}")
                    }
                }
            }
        } else onBackPressed()

    }

    @IdRes
    private fun getGender(code: Int) : Int = when (code) {
        0 -> R.id.user_gender_female
        1 -> R.id.user_gender_male
        else -> R.id.user_gender_secret
    }

    private fun getGenderCode(@IdRes id: Int) : Int = when (id) {
        R.id.user_gender_female -> 0
        R.id.user_gender_male -> 1
        else -> 2
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_user_profile, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        else if (item.itemId == R.id.menu_profile_done) editName()
        return super.onOptionsItemSelected(item)
    }
}

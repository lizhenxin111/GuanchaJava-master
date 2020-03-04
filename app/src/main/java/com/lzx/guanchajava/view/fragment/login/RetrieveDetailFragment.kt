package com.lzx.guanchajava.view.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.captcha.Captcha
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.find
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

class RetrieveDetailFragment : Fragment() {

    companion object {
        fun newInstance(phone: String, captcha: String) : RetrieveDetailFragment = RetrieveDetailFragment().apply {
            arguments = Bundle().apply {
                putString("PHONE", phone)
                putString("CAPTCHA", captcha)
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_retrieve_detail, container, false)
        val button = v.find<MaterialButton>(R.id.retrieve_detail_button)
        val password = v.find<TextInputEditText>(R.id.retrieve_detail_password)
        val again = v.find<TextInputEditText>(R.id.retrieve_detail_again)

        button.onClick {
            if (password.text.isNullOrBlank()) {
                toast("请输入密码")
                return@onClick
            }
            if (!password.text.toString().equals(again.text.toString())) {
                toast("两次输入不一致")
                return@onClick
            }

            Fuel.post(Api.retrieve(), listOf("mobile" to arguments!!.getString("PHONE"),
                    "captcha" to arguments!!.getString("CAPTCHA"),
                    "password" to password.text.toString())).responseObject(Captcha.Deserializer()) {_,_,result->
                val(data, error) = result
                if (error != null) {
                    App.context.longToast(ResourceUtil.getString(R.string.str_no_network))
                } else {
                    toast(result.get().msg)
                    if (result.get().code == 0) activity!!.onBackPressed()
                }
            }
        }
        return v
    }
}
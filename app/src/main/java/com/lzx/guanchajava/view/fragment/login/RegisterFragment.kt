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
import com.lzx.guanchajava.view.widget.CaptchaTextView
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

class RegisterFragment : Fragment() {

    lateinit var onRegisterClickListener: OnRegisterClickListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_register, container, false)
        val name = v.find<TextInputEditText>(R.id.register_name)
        val phone = v.find<TextInputEditText>(R.id.register_phone)
        val password = v.find<TextInputEditText>(R.id.register_passwrod)
        val captcha = v.find<CaptchaTextView>(R.id.register_captcha)
        captcha.mOnClickListener = View.OnClickListener {
            Fuel.post(Api.sendCaptcha(), listOf("mobile" to phone, "phone_code" to "86")).responseObject(Captcha.Deserializer()) {_,_,result ->
                toast(result.get().msg)
            }
        }

        v.find<MaterialButton>(R.id.register_btn).onClick {
            onRegisterClickListener.onRegisterClick(name.text.toString(), phone.text.toString(), password.text.toString(), captcha.captcha())
        }
        return v
    }


    interface OnRegisterClickListener {
        fun onRegisterClick(userName: String, phone: String, password: String, captcha: String)
    }

}
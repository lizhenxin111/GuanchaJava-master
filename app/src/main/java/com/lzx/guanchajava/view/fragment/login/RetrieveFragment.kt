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
import com.lzx.guanchajava.view.widget.CaptchaTextView
import org.jetbrains.anko.find
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

class RetrieveFragment : Fragment() {

    lateinit var onRetrieveClickListener: OnRetrieveClickListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_retrieve, container, false)
        val phone = v.find<TextInputEditText>(R.id.retrieve_phone)
        val captcha = v.find<CaptchaTextView>(R.id.retrieve_captcha)
        captcha.mOnClickListener = View.OnClickListener {
            Fuel.post(Api.sendCaptcha(), listOf("mobile" to phone, "phone_code" to "86")).responseObject(Captcha.Deserializer()) { _, response, result ->
                val (data, error) = result
                if (error != null) {
                    App.context.longToast(ResourceUtil.getString(R.string.str_no_network))
                } else {
                    toast(result.get().msg)
                }
            }
        }
        v.find<MaterialButton>(R.id.retrieve_button).onClick {
            onRetrieveClickListener.onRetrieveClick(phone.text.toString(), captcha.captcha())
        }
        return v
    }

    interface OnRetrieveClickListener {
        fun onRetrieveClick(phone: String, captcha: String)
    }
}
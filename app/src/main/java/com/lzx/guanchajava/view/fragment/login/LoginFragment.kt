package com.lzx.guanchajava.view.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.lzx.guanchajava.R
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class LoginFragment : Fragment() {

    lateinit var onLoginClickListener: OnLoginClickListener
    interface OnLoginClickListener {
        fun onLoginClick(phone: String, password: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_login, container, false)
        val phone = v.find<TextInputEditText>(R.id.login_phone_edit)
        val passwd = v.find<TextInputEditText>(R.id.login_passwd_edit)
        v.find<MaterialButton>(R.id.login_btn).onClick {
            onLoginClickListener.onLoginClick(phone.text.toString(), passwd.text.toString())
        }
        return v
    }
}
package com.lzx.guanchajava.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick


class CaptchaTextView : ConstraintLayout {

    private var mTime = 60

    private val text: TextInputEditText
    private val btn: Chip

    lateinit var mOnClickListener : OnClickListener

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context, attr, defStyle)

    init {
        val v = LayoutInflater.from(context).inflate(com.lzx.guanchajava.R.layout.layout_captcha_text, this, true)
        btn = v.find(com.lzx.guanchajava.R.id.captcha_button)
        text = v.find(com.lzx.guanchajava.R.id.captcha_text)

        btn.onClick {
            btn.isClickable = false
            mOnClickListener.onClick(btn)
            sendRunnable()
        }
    }

    fun captcha()  = text.text.toString()

    private fun sendRunnable() {
        handler.postDelayed(mCaptchaRunnable, 0)
    }

    private val mCaptchaRunnable = object : Runnable {
        override fun run() {
            if (mTime == 0) {
                btn.isClickable = true
            } else {
                mTime--
                handler.postDelayed(this, 1000)
                btn.text = mTime.toString()
            }
        }
    }
}
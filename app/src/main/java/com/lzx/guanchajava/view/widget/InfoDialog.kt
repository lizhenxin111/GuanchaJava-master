package com.lzx.guanchajava.view.widget

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.ResourceUtil
import com.lzx.guanchajava.util.SpUtil

class InfoDialog {
    private lateinit var mDialog : AlertDialog
    lateinit var onDialogButtonClick: OnDialogButtonClick

    private fun initDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(ResourceUtil.getString(R.string.statement_title)).
                setView(R.layout.layout_term)
                /*setMessage(ResourceUtil.getString(R.string.statement_content))*/
        if (!SpUtil.agreeTerms()) {
            builder.setNegativeButton(ResourceUtil.getString(R.string.statement_disagree)) { dialog, which ->
                if (::onDialogButtonClick.isInitialized) {
                    onDialogButtonClick.onNegativeClick(this@InfoDialog)
                }
            }.setPositiveButton(ResourceUtil.getString(R.string.statement_agree)) { dialog, which ->
                if (::onDialogButtonClick.isInitialized) {
                    onDialogButtonClick.onPositiveClick(this@InfoDialog)
                }
            }.setCancelable(false)
        }
        mDialog = builder.create()
    }

    fun show(context: Context) {
        if (!::mDialog.isInitialized) initDialog(context)
        mDialog.show()
    }

    fun dismiss(context: Context) {
        if (::mDialog.isInitialized) mDialog.dismiss()
    }

    interface OnDialogButtonClick {
        fun onPositiveClick(dialog: InfoDialog)
        fun onNegativeClick(dialog: InfoDialog)
    }
}
package com.lzx.guanchajava.view.widget

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class DialogUtils(val context: Context) {
    private lateinit var dialog: AlertDialog
    private var builder: AlertDialog.Builder = AlertDialog.Builder(context)

    fun showNormal(title: String, message: String) : DialogUtils {
        builder.setTitle(title)
                .setMessage(message)
        return this
    }


    fun setPositiveButton(text: String, listener: DialogInterface.OnClickListener): DialogUtils {
        builder.setPositiveButton(text, listener)
        return this
    }
    fun setNegativeButton(text: String, listener: DialogInterface.OnClickListener): DialogUtils {
        builder.setNegativeButton(text, listener)
        return this
    }


    fun show() {
        if (!::dialog.isInitialized)
            dialog = builder.create()
        if (!dialog.isShowing)
            dialog.show()
    }

    fun dismiss() {
        if (::dialog.isInitialized && dialog.isShowing) dialog.dismiss()
    }
}
package com.lzx.guanchajava.util

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.lzx.guanchajava.R

object PopupUtils {
    fun popupDelete(ancherView: View, callback:(MenuItem) -> Unit) {
        val popup = PopupMenu(ancherView.context, ancherView, Gravity.BOTTOM)
        popup.menuInflater.inflate(R.menu.menu_delete, popup.menu)
        popup.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_delete) {
                callback(it)
            }
            popup.dismiss()
            true
        }
        popup.show()
    }

    fun popupComment(ancherView: View, callback: (MenuItem) -> Unit) {
        val popup = PopupMenu(ancherView.context, ancherView, Gravity.BOTTOM)
        popup.menuInflater.inflate(R.menu.menu_comment_popup, popup.menu)
        popup.setOnMenuItemClickListener {
            if (it.itemId == R.id.comment_collect) {
                callback(it)
            }
            popup.dismiss()
            true
        }
        popup.show()
    }
}
package com.lzx.guanchajava.adapter.recomendAdapter

import android.view.View

interface OnClickListener {
    fun onClick(id: String, type: String)
    fun onLongClick(url: String, ancherView: View)
}
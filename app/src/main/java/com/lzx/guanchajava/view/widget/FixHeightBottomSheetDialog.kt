package com.lzx.guanchajava.view.widget

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class FixHeightBottomSheetDialog : BottomSheetDialog {
    constructor(context: Context, theme: Int) : super(context, theme)

    constructor(context: Context) : super(context)

    private lateinit var mContentView: View

    override fun onStart() {
        super.onStart()
        fixHeight()
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        mContentView = view
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        mContentView = view
    }

    private fun fixHeight() {
        if (!::mContentView.isInitialized) return

        val parent = mContentView.parent as View
        val behavior = BottomSheetBehavior.from(parent)
        mContentView.measure(0, 0)
        behavior.peekHeight = mContentView.measuredHeight

        val params = parent.layoutParams as CoordinatorLayout.LayoutParams
        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        parent.layoutParams = params
    }
}
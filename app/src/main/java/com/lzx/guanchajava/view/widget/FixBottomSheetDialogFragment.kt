package com.lzx.guanchajava.view.widget

import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class FixBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var mBottomSheetBehavior: BottomSheetBehavior<*>? = null


    override fun onStart() {
        super.onStart()
        val view = view!!
        val parent = view.parent as View
        val params = parent.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior
        mBottomSheetBehavior = behavior as BottomSheetBehavior<CoordinatorLayout>

        parent.setBackgroundColor(Color.TRANSPARENT);

        val display = activity!!.windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)
        mBottomSheetBehavior!!.peekHeight = point.y
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return FixHeightBottomSheetDialog(context!!)
    }
}
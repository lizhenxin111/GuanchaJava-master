package com.lzx.guanchajava.view.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.jetbrains.anko.sp

class TextFAB : FloatingActionButton {

    var text : String = ""
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context, attr, defStyle)

    /*override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
    }*/

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        val paint = Paint()
        val textWidth = paint.measureText(text)
        canvas.drawText(text,
                measuredWidth.toFloat() / 2 - textWidth / 2,
                measuredHeight.toFloat() / 2,
                paint.apply {
                    color = Color.BLACK
                    textSize = sp(8).toFloat()
                    typeface = Typeface.DEFAULT_BOLD
                })
        canvas.restore()
    }
}
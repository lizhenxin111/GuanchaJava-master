package com.lzx.guanchajava.view.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.defaultSharedPreferences


/**
 * 圆形https://blog.csdn.net/RockyHua/article/details/79416085
 */
const val ACTION_LOAD_IMAGE = "ACTION_LOAD_IMAGE"
class UrlImageView : ImageView {

    var showAlways = false  //一直显示.不受无图模式影响
    var url : String = ""
        set(value) {
            field = value
            loadImage()
        }

    private var mShowHolder = false     //是否显示占位图。显示占位图时,点击占位图加载原图

    //画笔
    private var mPaint = Paint()
    //圆形图片的半径
    private var mRadius: Float = 0f
    //图片的宿放比例
    private var mScale: Float = 0f

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context) : super(context)


    lateinit var onGetBitmapListener: OnGetBitmapListener
    var ifCircle = false

    private fun loadImage() {
        if (!App.context.defaultSharedPreferences.getBoolean("pref_no_pic", false) || showAlways) {
            loadFromNet()
            mShowHolder = false
        } else {
            loadFromPalceholder()
            mShowHolder = true
        }
    }

    private fun loadFromNet() {
        Glide.with(App.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
    }
    private fun loadFromPalceholder() {
        setImageDrawable(context.getDrawable(R.drawable.img_placeholder))
    }

    override fun setOnClickListener(l: OnClickListener?) {
        if (mShowHolder) {
            loadFromNet()
        }
        super.setOnClickListener(l)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (ifCircle) {
            //由于是圆形，宽高应保持一致
            val size = Math.min(measuredWidth, measuredHeight)
            mRadius = (size / 2).toFloat()
            setMeasuredDimension(size, size)
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (null != drawable && ifCircle) {
            mPaint = Paint()

            val drawable = drawable
            if (drawable is BitmapDrawable) {
                val bitmap = drawable.bitmap
                //初始化BitmapShader，传入bitmap对象
                val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                //计算缩放比例
                mScale = if (bitmap != null) mRadius * 2.0f / Math.min(bitmap.height, bitmap.width) else 1f

                val matrix = Matrix()
                matrix.setScale(mScale, mScale)
                bitmapShader.setLocalMatrix(matrix)
                mPaint.shader = bitmapShader
                //画圆形，指定好坐标，半径，画笔
                canvas.drawCircle(mRadius, mRadius, mRadius, mPaint)
            }
        } else {
            super.onDraw(canvas)
        }

        if (App.context.defaultSharedPreferences.getBoolean(TAG.PREF_NIGHT_MODE, false))
            canvas.drawColor(ResourceUtil.getColor(R.color.imageForeground))
    }

    interface OnGetBitmapListener {
        fun handleBitmap(bmp: Bitmap?)
    }

    inner class ImageBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            loadImage()
        }
    }

    inner class ImageReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action == ACTION_LOAD_IMAGE) loadImage()
        }

    }
}
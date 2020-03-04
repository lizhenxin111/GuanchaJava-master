package com.lzx.guanchajava.view.widget

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.ActionMode
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.view.activity.ImageActivity
import com.lzx.guanchajava.view.activity.NewsActivity
import com.lzx.guanchajava.util.CopyUtil
import com.lzx.guanchajava.util.SettingUtil
import org.jetbrains.anko.browse
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/**
 * 自定义WebView，实现调整html，图片点击，链接跳转，长按菜单
 * @property onWebViewFinishListener OnWebViewFinishListener  内容加载完成的回调
 * @property onNoteListener OnNoteListener  选中文字后添加笔记的回调
 */
class ContentWebView : WebView {

    lateinit var onWebViewFinishListener: OnWebViewFinishListener
    lateinit var onNoteListener: OnNoteListener

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context, attr, defStyle)

    /**
     * 加载html内容（过程中会经过清理）
     * @param html String   html字符串
     */
    fun loadHtml(html: String) {
        loadData(handleHtml(html), "text/html;charset=UTF-8", null)     //参数二加入;charset=UTF-8防止乱码
    }

    /**
     * 防止内存泄漏
     */
    fun releaseAllWebViewCallback() {
        if (android.os.Build.VERSION.SDK_INT < 16) {
            try {
                var field = WebView::class.java.getDeclaredField("mWebViewCore")
                field = field.type.getDeclaredField("mBrowserFrame")
                field = field.type.getDeclaredField("sConfigCallback")
                field.isAccessible = true
                field.set(null, null)
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        } else {
            try {
                val sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback")
                if (sConfigCallback != null) {
                    sConfigCallback.isAccessible = true
                    sConfigCallback.set(null, null)
                }
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
    }


    private val mImgOpacity : Float      //图片透明度
    private val mTextColor : String      //文字颜色
    private val mTextSize : String       //文字大小
    private var mActionMode: ActionMode? = null

    init {
        isHorizontalScrollBarEnabled = false
        isVerticalScrollBarEnabled = false

        mImgOpacity = if (SettingUtil.isNightMode()) 0.4f else 1f
        mTextColor = if (SettingUtil.isNightMode()) "#808080" else "#4f4f4f"
        mTextSize = SettingUtil.getTextSize()

        this.settings.javaScriptEnabled = true
        this.settings.blockNetworkImage = SettingUtil.isNoPic()
        this.addJavascriptInterface(PicClickClass(), "imageClick")
        this.webViewClient = MyWebViewClient()
        this.setBackgroundColor(Color.TRANSPARENT)

        addJavascriptInterface(SelectedJsInterface(), "JSInterface")
    }

    /**
     * 重写该方法,替换action以自定义菜单
     * @param callback ActionMode.Callback
     * @return ActionMode?
     */
    override fun startActionMode(callback: ActionMode.Callback): ActionMode? {
        val actionMode = super.startActionMode(callback)
        return resolveActionMode(actionMode)
    }

    override fun startActionMode(callback: ActionMode.Callback, type: Int): ActionMode? {
        val actionMode = super.startActionMode(callback, type)
        return resolveActionMode(actionMode)
    }

    /**
     * 处理item，处理点击
     * @param actionMode
     */
    private fun resolveActionMode(actionMode: ActionMode?): ActionMode? {
        if (actionMode != null) {
            var mActionList = listOf("复制", "添加笔记")
            val menu = actionMode.menu
            mActionMode = actionMode
            menu.clear()
            for (i in mActionList.indices) {
                menu.add(mActionList[i])
            }
            for (i in 0 until menu.size()) {
                val menuItem = menu.getItem(i)
                menuItem.setOnMenuItemClickListener { item ->
                    getSelectedData(item.getTitle() as String)
                    releaseAction()
                    true
                }
            }
        }
        mActionMode = actionMode
        return actionMode
    }

    /**
     * 释放action
     */
    private fun releaseAction() {
        if (mActionMode != null) {
            mActionMode!!.finish()
            mActionMode = null
        }
    }

    /**
     * 点击的时候，获取网页中选择的文本，回掉到原生中的js接口
     * @param title 传入点击的item文本，一起通过js返回给原生接口
     */
    private fun getSelectedData(title: String) {

        val js = "(function getSelectedText() {" +
                "   var txt;" +
                "   var title = \"" + title + "\";" +
                "   if (window.getSelection) {" +
                "       txt = window.getSelection().toString();" +
                "   } else if (window.document.getSelection) {" +
                "       txt = window.document.getSelection().toString();" +
                "   } else if (window.document.selection) {" +
                "       txt = window.document.selection.createRange().text;" +
                "   }" +
                "   JSInterface.callback(txt,title);" +
                "})()"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            evaluateJavascript("javascript:$js", null)
        } else {
            loadUrl("javascript:$js")
        }
    }


    /**
     * 处理html内容
     * @param html String
     */
    private fun handleHtml(html: String) : String {
        var content = "<html><header><meta name=\"content-type\" content=\"text/html; charset=utf-8\">" +
                "<meta http-equlv=\"Content-Type\" content=\"text/html;charset=utf-8\">" +                              //添加字符集限定
                "<style type=\"text/css\">body{color: $mTextColor; font-size: ${mTextSize}px;line-height:180%;}"        //调整文字颜色、大小、行距
        if (html.contains("alt=")) content += "img{width: 90%!important;opacity:$mImgOpacity;}"                             //若html含有alt=则调整图片大小；不含alt=的img为表情，不需要调整
        content += "</style></header><body>$html</body></html>"
        return content.replace("height=\"498\"", "height=\"200\"")
    }

    /**
     * 调用接口添加笔记
     * @param content String
     */
    private fun actionNote(content: String) {
        if (::onNoteListener.isInitialized) {
            onNoteListener.onSelected(content)
        }
    }

    inner class MyWebViewClient : android.webkit.WebViewClient() {
        /*override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url = view!!.url.toString()
            if (url!!.contains("guancha.cn")) {
                val id = url.substring(url.lastIndexOf("_")+1, url.lastIndexOf("."))
                context.startActivity<NewsActivity>("ID" to id, "CODETYPE" to "2")
            }  else {
                context.browse(url)
            }
            return true
        }*/
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url!!.contains("guancha.cn")) {
                val id = url.substring(url.lastIndexOf("_")+1, url.lastIndexOf("."))
                context.startActivity<NewsActivity>("ID" to id, "CODETYPE" to "2")
            }  else {
                context.browse(url)
            }
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            this@ContentWebView.loadUrl("javascript:(function(){" +
                    "var urls = document.getElementsByTagName(\"img\"); " +
                    "for(var i=0;i<urls.length;i++) {" +
                    "    urls[i].onclick=function() {  " +
                    "        window.imageClick.openImage(this.src);" +   //图片链接
                    "    }" +
                    "}" +
                    "})()")
            if (::onWebViewFinishListener.isInitialized) onWebViewFinishListener.onPageFinished(view, url)
        }
    }

    inner class PicClickClass {
        @JavascriptInterface
        fun openImage(url: String) {
            val intent = Intent(context, ImageActivity::class.java).apply {
                putExtra(TAG.ACTIVITY_IMAGE, url)
            }
            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(context as Activity).toBundle())
        }

        /*@JavascriptInterface
        fun openNews(url: String) {
            if (url!!.contains("guancha.cn")) {
                val id = url.substring(url.lastIndexOf("_")+1, url.lastIndexOf("."))
                App.context.startActivity<NewsActivity>("ID" to id, "CODETYPE" to "2")
            }  else {
                App.context.browse(url)
                //JumpUtil.startActivity(this@ContentWebView.context, WebActivity::class.java, "URL" to url)
            }
        }*/
    }

    inner class SelectedJsInterface {
        @JavascriptInterface
        fun callback(value: String, title: String) {
            when(title) {
                "复制" -> {
                    CopyUtil.copyToClipboard(value)
                    context.toast("已复制链接到剪贴板")
                }
                "添加笔记" -> actionNote(value)
            }
        }
    }

    interface OnWebViewFinishListener {
        fun onPageFinished(view: WebView?, url: String?)
    }
    interface OnNoteListener {
        fun onSelected(selectedText: String)
    }
}
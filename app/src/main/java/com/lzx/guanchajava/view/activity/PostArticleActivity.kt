package com.lzx.guanchajava.view.activity

import android.content.Intent
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.postArticle.PostArticle
import com.lzx.guanchajava.POJO.bean.postTopic.PostTopic
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.ResourceUtil
import com.lzx.guanchajava.util.SaveContent
import kotlinx.android.synthetic.main.activity_post_article.*
import kotlinx.android.synthetic.main.content_post_article.*
import org.jetbrains.anko.*

class PostArticleActivity : BaseActivity() {
    override fun loadUI() {
        setContentView(R.layout.activity_post_article)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "发帖"
    }

    override fun loadContent() {
        if (SaveContent.hasSavedArticle()) {
            post_article_title.setText(SaveContent.getSavedTitle())
            post_article_content.setText(SaveContent.getSavedContent())
        }
        loadTopics()
    }

    override fun loadOther() {
    }

    private var mSaveArticle = true


    private fun loadTopics() {
        Fuel.get(Api.postArticleTopics()).responseString { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val data = Gson().fromJson(result.get(), PostTopic::class.java).data.items
                for (x in data) {
                    post_article_topic.addView(newChip(x.name, x.topic_id.toString()))
                }
            }
        }
    }

    private fun postArticle() {
        showLoading()
        var checkedIds = mutableListOf<String>()
        for (x in 0 until post_article_topic.childCount) {
            val v = post_article_topic.getChildAt(x) as Chip
            if (v.isChecked) {
                checkedIds.add(v.tag.toString())
            }
        }
        if (checkedIds.isEmpty()) {
            Snackbar.make(post_article_container, "'请至少选择一个标签", Snackbar.LENGTH_SHORT).show()
            return
        }
        var topic = checkedIds[0]
        for (x in 1 until checkedIds.size) {
            topic += ",${checkedIds[x]}"
        }
        Fuel.post(Api.postArticle(), listOf("topic" to topic,
                "title" to post_article_title.text.toString().replace("&", "\\&"),
                "content" to post_article_content.text.toString().replace("&", "\\&"),
                "is_anonymous" to 0.toString(),
                "accessDevice" to 3.toString()))
                .responseString { request, response, result ->
                    val (data, error) = result
                    if (error != null) {
                        longToast(ResourceUtil.getString(R.string.str_no_network))
                    } else {
                        val data = Gson().fromJson(result.get().replace("[]", "{}"), PostArticle::class.java)
                        if (data.code == 0) {
                            toast("发帖成功")
                            startActivity<PostActivity>("ID" to data.data.post_id.toString(), "CODETYPE" to "2")
                            mSaveArticle = false
                            finishAfterTransition()
                        } else {
                            longToast("发帖失败：${data.msg}")
                            if (data.msg.contains("登录")) {
                                Snackbar.make(post_article_container, "请登录", Snackbar.LENGTH_LONG).setAction("登录") {
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivityForResult(intent, com.lzx.guanchajava.POJO.TAG.CODE_LOGIN_REQUEST)
                                }.show()
                            }
                        }
                        dismissLoading()
                    }
                }
    }


    private fun addImage() {
        Fuel.post("")
                .header(Headers.CONTENT_TYPE, "image/jpeg")
    }

    private fun newChip(t: String, id: String) = Chip(this).apply {
        text = t
        tag = id
        isCheckable = true
        textColor = ContextCompat.getColor(context, R.color.textColor)
        textSize = sp(4).toFloat()
        gravity = Gravity.CENTER
        //backgroundColor = ContextCompat.getColor(context, R.color.colorChipBackground)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_post_article, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) onBackPressed()
        else postArticle()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mSaveArticle) {
            SaveContent.savePostArticle(post_article_title.text.toString(),
                    post_article_content.text.toString())
        }
        SaveContent.setSaveArticle(mSaveArticle)
    }
}

package com.lzx.guanchajava.view.activity

import android.view.Menu
import android.view.MenuItem
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.Feedback
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.InputUtil
import com.lzx.guanchajava.util.ResourceUtil
import kotlinx.android.synthetic.main.activity_feedback.*
import org.jetbrains.anko.longToast

class FeedbackActivity : BaseActivity() {
    override fun loadUI() {
        setContentView(R.layout.activity_feedback)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "反馈"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun loadContent() {

    }

    override fun loadOther() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_post_article, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun sendFeedback() {
        showLoading()
        val bean = Feedback(feedback_title.text.toString(), feedback_content.text.toString())
        val json = Gson().toJson(bean)
        Fuel.post(Api.feedback()).jsonBody(json).responseString { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                if (response.statusCode == 201) {
                    Snackbar.make(feedback_container, "反馈成功，感谢您积极参与App的调研", Snackbar.LENGTH_INDEFINITE).setAction("返回") { finish() }.show()
                } else {
                    Snackbar.make(feedback_container, "反馈失败，请稍后再试", Snackbar.LENGTH_LONG).show()
                }
                InputUtil.toggleKeyboardState()
                dismissLoading()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        } else if (item!!.itemId == R.id.post_send) {
            sendFeedback()
        }
        return super.onOptionsItemSelected(item)
    }
}

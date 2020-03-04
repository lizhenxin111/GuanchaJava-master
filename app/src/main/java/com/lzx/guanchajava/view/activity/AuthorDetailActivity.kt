package com.lzx.guanchajava.view.activity

import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.authorDetail.AuthorDetail
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.authorAdapter.AuthorDetailAdapter
import com.lzx.guanchajava.util.ImageUtils
import kotlinx.android.synthetic.main.activity_author_detail.*

class AuthorDetailActivity : BaseActivity() {

    private lateinit var mAdapter : AuthorDetailAdapter
    override fun loadUI() {
        setContentView(R.layout.activity_author_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        author_articles.layoutManager = LinearLayoutManager(baseContext)
        mAdapter = AuthorDetailAdapter(onClickListener)
        author_articles.adapter = mAdapter
    }

    override fun loadContent() {
        Fuel.get(Api.zjxq(intent.getStringExtra("ID"), 1.toString())).responseString { request, response, result ->
            val list = Gson().fromJson(result.get(), AuthorDetail::class.java).datas

            if (!list.isNullOrEmpty()) {
                val f = list[0].author[0]
                author_pic.url = f.pic
                author_name.text = f.name
                author_des.text = f.summary
                author_article.text = "文章：${f.article_count}篇"


                mAdapter.data = list
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun loadOther() {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private val onClickListener = object : AuthorDetailAdapter.OnClickListener {
        override fun onClick(id: String, type: String) {
            goto(NewsActivity::class.java, "ID" to id, "CODETYPE" to "1")
        }

        override fun onLongClick(imageUrl: String, ancherView: View) {
            ImageUtils.saveImageWithPopup(imageUrl, ancherView)
        }

    }

}

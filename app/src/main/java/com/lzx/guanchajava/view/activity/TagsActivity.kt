package com.lzx.guanchajava.view.activity

import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.newsList.News
import com.lzx.guanchajava.POJO.bean.newsList.NewsList
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.newsListAdapter.*
import com.lzx.guanchajava.util.ImageUtils
import com.lzx.guanchajava.util.JumpUtil
import kotlinx.android.synthetic.main.activity_tags.*
import kotlinx.android.synthetic.main.content_tags.*

class TagsActivity : BaseActivity() {

    private lateinit var mId : String

    override fun loadUI() {
        setContentView(R.layout.activity_tags)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        tag_list.layoutManager = LinearLayoutManager(this)
    }

    override fun loadContent() {
        Fuel.get(Api.bqnr(mId)).responseObject(NewsList.Deserializer()) {_,_,result ->
            val list = result.get().data.items
            for (x in list) {
                if (x.special != null) {
                    supportActionBar?.title = x.special.name
                    break
                }
            }
            tag_list.adapter = NewsListAdapter().apply {
                types = listOf(Type1(onTagClickObj), Type2(onTagClickObj), Type3(onTagClickObj), Type4(onTagClickObj), Type5(onTagClickObj))
                onClickListener = object : NewsListAdapter.OnClickListener {
                    override fun onClick(type: Int, id: String) {
                        when (type) {
                            1 -> {
                                goto(NewsActivity::class.java, "ID" to id, "CODETYPE" to "1")
                            }     //新闻
                            2 -> {
                                goto(PostActivity::class.java,"ID" to id, "CODETYPE" to "2")
                            }     //风闻
                            3 -> toTagsAct(id)      //标签
                            else -> {

                            }
                        }
                    }
                }
                data = list as MutableList<News>
            }
        }
    }

    override fun loadOther() {
        mId = intent.getStringExtra(TAG.ACTIVITY_TAGS)
    }

    private val onTagClickObj = object : INewsType.OnClickListener {
        override fun onTagClick(id: String) {
            toTagsAct(id)
        }

        override fun onLongClick(imageUrl: String, ancherView: View) {
            ImageUtils.saveImageWithPopup(imageUrl, ancherView)
        }

        override fun onClick(id: String, type: String) {
            if (type == "1") JumpUtil.startActivity(this@TagsActivity, NewsActivity::class.java, "ID" to id, "CODETYPE" to type)
            else JumpUtil.startActivity(this@TagsActivity, PostActivity::class.java, "ID" to id, "CODETYPE" to type)
        }
    }

    private fun toTagsAct(id: String) {
        goto(TagsActivity::class.java,TAG.ACTIVITY_TAGS to id)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}

package com.lzx.guanchajava.view.activity

import android.graphics.PixelFormat
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.db.DBHistory
import com.lzx.guanchajava.POJO.bean.operate.collectArticle.CollectArticle
import com.lzx.guanchajava.POJO.bean.operate.praiseArticle.PraiseTopic
import com.lzx.guanchajava.POJO.bean.recomend.news.MostNew
import com.lzx.guanchajava.POJO.bean.recomend.news.NewsRecomend
import com.lzx.guanchajava.POJO.bean.recomend.news.Special
import com.lzx.guanchajava.POJO.bean.recomend.post.Hot24
import com.lzx.guanchajava.POJO.bean.recomend.post.Newest
import com.lzx.guanchajava.POJO.bean.recomend.post.PostRecomend
import com.lzx.guanchajava.POJO.bean.recomend.post.Recommend
import com.lzx.guanchajava.POJO.bean.topic.TopicX
import com.lzx.guanchajava.POJO.bean.topic.VoteInfo
import com.lzx.guanchajava.POJO.bean.voteResult.VoteResult
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.Vote.VoteAdapter
import com.lzx.guanchajava.adapter.recomendAdapter.*
import com.lzx.guanchajava.db.HistoryDB
import com.lzx.guanchajava.view.fragment.reply.ReplyFragment
import com.lzx.guanchajava.view.fragment.share.ShareFragment
import com.lzx.guanchajava.member.db.NoteDB
import com.lzx.guanchajava.member.fragment.AddNoteFragment
import com.lzx.guanchajava.member.fragment.NoteListFragment
import com.lzx.guanchajava.util.*
import com.lzx.guanchajava.view.widget.ContentWebView
import com.lzx.guanchajava.view.widget.EmptyRecyclerView
import com.lzx.guanchajava.view.widget.UrlImageView
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.content_news.*
import org.jetbrains.anko.find
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk27.coroutines.onClick


/**
 * 新闻/风闻详情页
 * ID:文章的id
 * CODETYPE：评论类型,codetype==1：新闻；codetype==2：风闻
 */
abstract class BaseWebActivity : BaseActivity() {

    protected lateinit var mArticleId: String
    protected lateinit var mCodeType: String
    protected lateinit var mArticleTitle: String
    protected lateinit var mOpenTime: String
    private lateinit var mReplyId : String
    private lateinit var mCurrentHtml: String

    protected var ifInsertNews = true

    private var isInitMenu = false

    private var isCollect = false
    private var isPraise = false

    private val mHandler = Handler()

    override fun loadUI() {
        window.setFormat(PixelFormat.TRANSLUCENT)
        setContentView(R.layout.activity_news)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (mCodeType == "1") news_author_pic.visibility = View.GONE


        news_comment.onClick {
            ReplyFragment.newInstance(mReplyId, mCodeType, mArticleTitle).show(supportFragmentManager, "REPLY")
            //startActivity<ReplyActivity>("ID" to mArticleId, "TYPE" to mCodeType)
        }
    }

    override fun loadContent() {
        getContent()
        if (mCodeType == "2") setTips()

        if (SettingUtil.isShowRecommend()) {
            mHandler.postDelayed({
                if (mCodeType == "1") setNewsRecomend()
                else setPostRecomend()
            }, 1000)
        }
    }

    override fun loadOther() {
        mArticleId = intent.getStringExtra("ID")
        mCodeType = intent.getStringExtra("CODETYPE")
        mOpenTime = TimeUtil.timeMinute()
    }

    abstract fun getContent()

    protected fun setArticleInfo(title: String, summary: String, tags: List<TopicX>, replyId: String) {
        mReplyId = replyId
        news_title.text = title
        mArticleTitle = title
        if (summary != "") news_summary.text = summary

        for (x in tags) {
            news_tag_list.addView(
                    MaterialButton(this@BaseWebActivity).apply {
                        text = x.topic_name
                        onClick { onTagClick(x.topic_id.toString()) }
                    }
            )
        }
    }

    /**
     * @hasSubscribe: 0：无此项（即专栏作家的新闻页面，而非风闻页面）； 1：未关注;  2：已关注
     */
    protected fun setAuthorInfo(id: String, name: String, pic: String, time: String) {
        news_author_pic.url = pic
        news_author_pic.ifCircle = true
        news_author_pic.onClick { goto(UserDetailActivity::class.java, "UID" to id) }
        news_author_name.text = name
        news_time.text = time
    }

    protected fun setContent(content: String) {
        if (!::mCurrentHtml.isInitialized) {
            mCurrentHtml = content
        }
        var mContentWithNote = content
        val notes = NoteDB.queryAllNoteById(mArticleId)
        for (x in notes) {
            mContentWithNote = mContentWithNote.replace(x.content, handleNoteString(x.content).trim())
        }
        news_webview.apply {
            loadHtml(mContentWithNote)
            onNoteListener = onNoteClick
        }
    }

    protected fun setEditorInfo(source: String, name: String, time: String) {
        news_editor.visibility = View.VISIBLE
        news_editor.text = "新闻来源：$source \n责任编辑：$name  于${time.substring(0, 10)}"
    }

    protected fun setNumbers(replySum: Int, isCollected: Boolean, isPraised: Boolean) {
        news_comment.text = replySum.toString()
        this.isCollect = isCollected
        this.isPraise = isPraised
        invalidateOptionsMenu()
    }

    protected fun setVote(vote: VoteInfo) {
        news_vote_container.visibility = ViewGroup.VISIBLE
        if (vote.has_voted) showVoted(vote)
        else showVote(vote)
    }

    private fun setTips() {
        news_tips.visibility = View.VISIBLE
    }

    /**
     * 处理被记笔记的文字,加粗和颜色
     * @param content String
     * @return String
     */
    private fun handleNoteString(content: String)
            = "<font color=\"#FF0000\">$content</font>"

    /**
     * 重新加载内容(刷新笔记)
     */
    private fun reload() {
        news_webview.loadHtml("")
        setContent(mCurrentHtml)
    }

    private fun onTagClick(id: String) {
        goto(TagsActivity::class.java, TAG.ACTIVITY_TAGS to id)
    }

    /**
     * 未投票
     */
    private fun showVote(vote: VoteInfo) {
        val vg = layoutInflater.inflate(R.layout.layout_vote, null, false)
        val title = vg.find<TextView>(R.id.news_vote_title)
        val list = vg.find<LinearLayout>(R.id.news_vote_list)
        val button = vg.find<MaterialButton>(R.id.vote_button)
        val limit = vg.find<TextView>(R.id.vote_limit)

        title.text = vote.vote_title
        limit.text = "您最多可以选择${vote.vote_limit}个"
        button.onClick { publishVote(vote.vote_id.toString(), list, vote.vote_limit) }
        for (x in vote.vote_options) {
            list.addView(CheckBox(this).apply {
                text = x.option_name
                id = x.option_id
            })
        }
        news_vote_container.addView(vg)
    }

    /**
     * 发起投票
     */
    private fun publishVote(voteId: String, list: LinearLayout, max: Int) {
        var voteSum = 0     //投票总数

        var ids = mutableListOf<Int>()
        for (x in 0 until list.childCount) {
            val v = list.getChildAt(x) as CheckBox
            if (v.isChecked) {
                ids.add(v.id)
                voteSum++
            }
            if (voteSum > max) {
                Toast.makeText(this, "您选择的项目不应超过${max}个", Toast.LENGTH_LONG).show()
                return
            }
        }   //统计选择的数量

        if (voteSum == 0) {     //未投票
            Toast.makeText(this, "您未选择任何项目", Toast.LENGTH_LONG).show()
            return
        }

        var voteIds = ids[0].toString()
        for (x in 1 until ids.size) {    //构建参数
            voteIds += ",$x"
        }

        Fuel.post(Api.voteTo(), listOf("post_id" to mArticleId, "vote_extend_ids" to voteIds)).responseObject(VoteResult.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val voteResult = result.get()
                if (voteResult.code == 0) {
                    news_vote_container.removeAllViews()
                    showVoted(voteResult.data[0])
                    Toast.makeText(this, "投票成功", Toast.LENGTH_LONG).show()
                } else Toast.makeText(this, "投票失败，请重试", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * 已投票
     */
    private fun showVoted(vote: VoteInfo) {
        val vg = layoutInflater.inflate(R.layout.layout_voted, null, false)
        val title = vg.find<TextView>(R.id.news_has_vote_title)
        val list = vg.find<EmptyRecyclerView>(R.id.news_has_vote_list)
        val sum = vg.find<TextView>(R.id.news_has_vote_sum)

        title.text = vote.vote_title
        sum.text = "共${vote.total_vote}人投票"
        for (x in vote.vote_options) x.vote_sum = vote.total_vote
        list.apply {
            layoutManager = LinearLayoutManager(this@BaseWebActivity)
            adapter = VoteAdapter().apply { data = vote.vote_options }
        }
        news_vote_container.addView(vg)
    }


    /**
     * 新闻推荐
     */
    private fun setNewsRecomend() {
        val v = layoutInflater.inflate(R.layout.layout_recommend_news, news_recomend_container, true)
        Fuel.get(Api.newsRecomend(mArticleId)).responseString { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                //Logger.d(result.get())
                val resultString = result.get()
                try {
                    var data = Gson().fromJson(resultString, NewsRecomend::class.java).datas
                    setNewsRecomendSpecial(v, data!!.special)
                    setNewsRecomendHot(v, data!!.most_news)
                } catch (e: Exception) {
                    val r = resultString.replace("\"list\":", "\"list\":[").replace("}},\"most_news\"", "}]},\"most_news\"")
                    var data = Gson().fromJson(r, NewsRecomend::class.java).datas
                    setNewsRecomendSpecial(v, data.special)
                    setNewsRecomendHot(v, data.most_news)
                }

            }
        }
    }

    /**
     * 新闻推荐之专题
     */
    private fun setNewsRecomendSpecial(v: View, special: Special) {
        val recomend_topic_container = v.find<ConstraintLayout>(R.id.recomend_topic_container)
        if (special.id.isNullOrBlank()) recomend_topic_container.visibility = View.GONE
        else {
            val title = v.find<TextView>(R.id.recomend_topic_title)
            val pic = v.find<UrlImageView>(R.id.recomend_topic_pic)
            val title1 = v.find<TextView>(R.id.recomend_topic_title1)
            val title2 = v.find<TextView>(R.id.recomend_topic_title2)

            title.text = "------${special.name}--------"
            pic.url = special.pic.toString()

            val t1 = special.list[0]
            title1.text = t1.title
            title1.onClick { goto(NewsActivity::class.java, "ID" to t1.id, "CODETYPE" to "1") }

            val t2 = special.list[1]
            title2.text = t2.title
            title2.onClick { goto(NewsActivity::class.java, "ID" to t2.id, "CODETYPE" to "1") }
        }
    }

    /**
     * 新闻推荐之最新闻
     */
    private fun setNewsRecomendHot(v: View, news: MutableList<MostNew>) {
        val recomend_hot = v.find<EmptyRecyclerView>(R.id.recomend_hot)
        recomend_hot.apply {
            layoutManager = LinearLayoutManager(this@BaseWebActivity)
            adapter = NewsRecomentAdapter(onRecomendHotClickListner).apply {
                data = news
            }
        }
    }


    /**
     * 风闻推荐
     */
    private fun setPostRecomend() {
        val v = layoutInflater.inflate(R.layout.layout_recommend_post, news_recomend_container, true)
        Fuel.get(Api.postRecomend(mArticleId)).responseString { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                //Logger.d(result.get())
                val resultString = result.get()
                try {
                    val data = Gson().fromJson(resultString, PostRecomend::class.java).data
                    if (!data.newest.isNullOrEmpty()) setPostRecommendNewest(v, data.newest)
                    if (!data.recommend.isNullOrEmpty()) setPostRecommendRecommend(v, data.recommend)
                    if (!data.hot24.isNullOrEmpty()) setPostRecommendHot(v, data.hot24)
                } catch (e: Exception) {
                    val r = resultString.replace("\"list\":", "\"list\":[").replace("}},\"most_news\"", "}]},\"most_news\"")
                    val data = Gson().fromJson(r, PostRecomend::class.java).data
                    if (!data.newest.isNullOrEmpty()) setPostRecommendNewest(v, data.newest)
                    if (!data.recommend.isNullOrEmpty()) setPostRecommendRecommend(v, data.recommend)
                    if (!data.hot24.isNullOrEmpty()) setPostRecommendHot(v, data.hot24)
                }
            }
        }
    }

    /**
     * 风闻推荐之作者文章
     */
    private fun setPostRecommendNewest(v: View, list: List<Newest>) {
        v.find<TextView>(R.id.recommend_author).visibility = View.VISIBLE
        v.find<EmptyRecyclerView>(R.id.recommend_author_list).apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(this@BaseWebActivity)
            adapter = NewestAdapter(onRecomendHotClickListner).apply {
                data = list as MutableList<Newest>
            }
        }
    }

    /**
     * 风闻推荐之相关推荐
     */
    private fun setPostRecommendRecommend(v: View, list: List<Recommend>) {
        v.find<EmptyRecyclerView>(R.id.recommend_related_list).apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(this@BaseWebActivity)
            adapter = PostRecommendAdapter(onRecomendHotClickListner).apply {
                data = list as MutableList<Recommend>
            }
        }
    }

    /**
     * 风闻推荐之24h最热
     */
    private fun setPostRecommendHot(v: View, list: List<Hot24>) {
        v.find<EmptyRecyclerView>(R.id.recommend_hot_list).apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(this@BaseWebActivity)
            adapter = HotAdapter(onRecomendHotClickListner).apply {
                data = list as MutableList<Hot24>
            }
        }
    }


    private val onRecomendHotClickListner = object : OnClickListener {
        override fun onLongClick(url: String, ancherView: View) {
            ImageUtils.saveImageWithPopup(url, ancherView)
        }

        override fun onClick(id: String, type: String) {
            if (type == "1") goto(NewsActivity::class.java, "ID" to id, "CODETYPE" to type)
            else goto(PostActivity::class.java, "ID" to id, "CODETYPE" to type)
        }
    }

    private fun collectArticle(item: MenuItem) {
        if (!LoginUtil.hasToken()) {
            Snackbar.make(news_author_container, "请登录", Snackbar.LENGTH_LONG).setAction("登录") { goto(LoginActivity::class.java) }.show()
            return
        }
        Fuel.post(Api.collectArticle(), listOf("code_id" to mArticleId, "from" to "2", "type" to mCodeType)).responseObject(CollectArticle.Deserializer()) { _, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val r = result.get().data.is_collect
                isCollect = r
                if (r) {
                    Snackbar.make(news_author_container, "收藏成功", Snackbar.LENGTH_SHORT).show()
                    item.icon = resources.getDrawable(R.drawable.ic_mark_fill_24dp)
                } else {
                    Snackbar.make(news_author_container, "取消收藏", Snackbar.LENGTH_SHORT).show()
                    item.icon = resources.getDrawable(R.drawable.ic_mark_24dp)
                }
                invalidateOptionsMenu()
            }
        }


    }

    private fun praiseArticle(item: MenuItem) {
        if (!LoginUtil.hasToken()) {
            Snackbar.make(news_author_container, "请登录", Snackbar.LENGTH_LONG).setAction("登录") { goto(LoginActivity::class.java) }.show()
            return
        }
        Fuel.post(Api.praiseTopic(), listOf("post_id" to mArticleId, "from" to "2")).responseObject(PraiseTopic.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val r = result.get().data.is_set
                isPraise = r
                if (r) {
                    Snackbar.make(news_author_container, "点赞成功", Snackbar.LENGTH_SHORT).show()
                    item.icon = App.context.getDrawable(R.drawable.ic_praise_fill_24dp)
                } else {
                    Snackbar.make(news_author_container, "取消点赞", Snackbar.LENGTH_SHORT).show()
                    item.icon = App.context.getDrawable(R.drawable.ic_praise_24dp)
                }
                invalidateOptionsMenu()

            }
        }
    }

    private val onNoteClick = object : ContentWebView.OnNoteListener {
        override fun onSelected(selectedText: String) {
            //检查会员
            AddNoteFragment.newInstance(mArticleId, mCodeType, mArticleTitle, selectedText).show(supportFragmentManager, "ADdNOTE")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        /*if (!isInitMenu) {

            isInitMenu = true
        }*/
        @MenuRes val id = if (mCodeType == "1" && !isCollect) R.menu.menu_news
        else if (mCodeType == "1" && isCollect) R.menu.menu_news_fill
        else if (mCodeType == "2" && !isCollect && !isPraise) R.menu.menu_topic
        else if (mCodeType == "2" && isCollect && !isPraise) R.menu.menu_topic_collected
        else if (mCodeType == "2" && !isCollect && isPraise) R.menu.menu_topic_praised
        else if (mCodeType == "2" && isCollect && isPraise) R.menu.menu_topic_fill
        else 0
        menuInflater.inflate(id, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.menu_collect -> {
                collectArticle(item)
            }
            R.id.menu_praise -> {
                praiseArticle(item)
            }
            R.id.menu_share -> {
                ShareFragment.newInstance(mArticleId, mCodeType).show(supportFragmentManager, "SHARE")
            }
            R.id.menu_note -> {
                NoteListFragment.newInstance(mArticleId).show(supportFragmentManager, "NOTE")
            }
            R.id.menu_refresh_webview -> {
                reload()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        news_webview.destroy()
        val history = DBHistory(mArticleId, mCodeType, mArticleTitle, mOpenTime, TimeUtil.timeMinute())
        HistoryDB.insertHistory(history)
        super.onDestroy()
    }
}

package com.lzx.guanchajava.view.activity

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.chip.Chip
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.search.Hotkey.Hotkey
import com.lzx.guanchajava.R
import com.lzx.guanchajava.db.SearchDB
import com.lzx.guanchajava.view.fragment.search.SearchNavFragment
import com.lzx.guanchajava.util.InputUtil
import com.lzx.guanchajava.util.ResourceUtil
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_search.*
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.coroutines.onQueryTextListener
import org.jetbrains.anko.sdk27.coroutines.onClick


class SearchActivity : BaseActivity() {
    override fun loadUI() {
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initHistory()
        search_clear.onClick {
            search_history.removeAllViews()
            clearSearchWord()
        }
    }

    override fun loadContent() {
        initHot()
    }

    override fun loadOther() {
    }

    private val mSearchResult = mutableListOf<String>()

    override fun onResume() {
        super.onResume()
        loadSearchHistory()
    }


    private fun initHot() {
        Fuel.get(Api.hotKey()).responseObject(Hotkey.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                for (x in result.get().data) {
                    search_hot.addView(newChip(x.keyword))
                }
            }
        }
    }

    private fun initHistory() {
        loadSearchHistory()
    }

    /**
     * 加载搜索记录
     * 将查询数据库的结果与现有结果对比，添加不包含在现有结果中的
     */
    private fun loadSearchHistory() {
        var result = SearchDB.queryWord()
        if (result.size > mSearchResult.size) {
            for (x in mSearchResult.size until result.size) {
                val t = result[x]
                search_history.addView(newChip(t.word))
                mSearchResult.add(t.word)
            }
        }

        if (!mSearchResult.isNullOrEmpty() && search_clear.visibility != View.VISIBLE) {
            search_clear.visibility = View.VISIBLE
        }
    }

    private fun loadSearchResult(word: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
                .replace(R.id.search_container, SearchNavFragment.newInstance(word))
                .addToBackStack("SEARCHRESULT")
                .commit()
    }

    private fun newChip(t: String) = Chip(this).apply {
        text = t
        onClick {
            loadSearchResult(t)
            insertSearchWord(t)
        }
        textColor = ContextCompat.getColor(context, R.color.textColor)
        textSize = sp(8).toFloat()
        backgroundColor = ContextCompat.getColor(context, R.color.colorChipBackground)
    }

    private fun insertSearchWord(word: String) {
        SearchDB.insertWord(word)
    }

    private fun clearSearchWord() {
        SearchDB.deleteAll()
        toast("清空所有搜索记录")
        search_clear.visibility = View.INVISIBLE
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStackImmediate()
            loadSearchHistory()
        } else super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search_detail, menu)

        //找到searchView
        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView

        //searchView.isIconified = false  //一开始处于展开状态
        searchView.onActionViewExpanded()   // 当展开无输入内容的时候，没有关闭的图标
        searchView.isSubmitButtonEnabled = true

        searchView.onQueryTextListener {
            this.onQueryTextSubmit {
                if (it.isNullOrBlank()) {
                    Toast.makeText(applicationContext, "搜索内容为空", Toast.LENGTH_SHORT).show()
                } else {
                    loadSearchResult(it)
                    insertSearchWord(it)
                    InputUtil.toggleKeyboardState()
                }
                return@onQueryTextSubmit true
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

}

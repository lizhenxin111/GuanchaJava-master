package com.lzx.guanchajava.db

import com.lzx.guanchajava.POJO.bean.db.DBSearch
import org.litepal.LitePal
import org.litepal.extension.deleteAll
import org.litepal.extension.find
import org.litepal.extension.findAll

object SearchDB {
    fun insertWord(word: String) {
        DBSearch(word).save()
    }

    fun deleteAll() {
        LitePal.deleteAll<DBSearch>()
    }

    fun queryWord(): MutableList<DBSearch> = LitePal.findAll<DBSearch>()

    fun checkExit(word: String) = LitePal.where("word = ?", word).find<DBSearch>().isNotEmpty()
}
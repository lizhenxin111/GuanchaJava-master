package com.lzx.guanchajava.db

import com.lzx.guanchajava.POJO.bean.db.DBHistory
import org.litepal.LitePal
import org.litepal.extension.deleteAll
import org.litepal.extension.find
import org.litepal.extension.findAll

object HistoryDB {
    fun insertHistory(history: DBHistory) {
        if (checkExit(history.article_id)) {
            deleteHistory(history.article_id)
        }
        history.save()
        /*history.saveOrUpdateAsync("article_id = ?", history.article_id)*/
    }

    fun deleteHistory(articleId: String) {
        LitePal.deleteAll<DBHistory>("article_id = ?", articleId)
    }

    fun queryHistory() : List<DBHistory> = LitePal.findAll<DBHistory>()

    fun checkExit(articleId: String)
            = LitePal.where("article_id = ?", articleId).find<DBHistory>().isNotEmpty()
}
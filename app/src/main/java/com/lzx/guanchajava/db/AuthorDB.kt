package com.lzx.guanchajava.db

import com.lzx.guanchajava.POJO.bean.db.DBAuthor
import org.litepal.LitePal
import org.litepal.extension.deleteAll
import org.litepal.extension.find
import org.litepal.extension.findAll

object AuthorDB {
    fun insertAuthor(author: DBAuthor) {
        author.save()
    }

    fun deleteAuthor(id: String) {
        LitePal.deleteAll<DBAuthor>("author_id = ?", id)
    }

    fun queryAuthor(): MutableList<DBAuthor> = LitePal.findAll<DBAuthor>()

    fun checkExit(id: String) = LitePal.where("author_id = ?", id).find<DBAuthor>().isNotEmpty()
}
package com.lzx.guanchajava.member.db

/*import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener*/
import com.lzx.guanchajava.member.bean.Article
import com.lzx.guanchajava.member.bean.DBNote
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.litepal.LitePal
import org.litepal.extension.deleteAll
import org.litepal.extension.find
import org.litepal.extension.findFirst

/**
 * 笔记的Litepal类
 */
object NoteDB {
    fun insertNote(note: DBNote) {
        note.saveOrUpdate("content = ?", note.content)
    }

    fun deleteNoteById(articleId: String) {
        LitePal.deleteAll<DBNote>("article_id = ?", articleId)
    }

    fun deleteNoteByContent(note: String) {
        LitePal.deleteAll<DBNote>("content = ?", note)
    }

    fun queryAllNoteById(articleId: String)
            = LitePal.where("article_id = ?", articleId).find<DBNote>()

    fun queryNoteByContent(content: String)
            = LitePal.where("content = ?", content).findFirst<DBNote>()

    fun queryAllArticle()
            = LitePal.findBySQL("select article_id, article_type, article_title from dbnote")
            .parseList(classParser<Article>())
            .toSet()        //去掉重复
            .toMutableList()
}
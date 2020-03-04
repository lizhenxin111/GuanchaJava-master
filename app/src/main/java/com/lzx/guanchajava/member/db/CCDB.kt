package com.lzx.guanchajava.member.db

/*import cn.bmob.v3.BmobQuery
import cn.bmob.v3.datatype.BmobDate
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener*/
import com.lzx.guanchajava.POJO.bean.reply.Reply
import com.lzx.guanchajava.POJO.bean.userDetail.reply.Comment
import com.lzx.guanchajava.POJO.bean.userDetail.reply.ParentComment
import com.lzx.guanchajava.member.bean.DBComment
import org.litepal.LitePal
import org.litepal.extension.findAll

/**
 * 收藏评论的数据库操作类
 *
 * var title : String,
var code_id : String,
var code_type : String,
var user_photo : String,
var user_id : String,
var user_nick : String,
var content : String
 */
object CCDB {
    fun insertComment(comment: DBComment) {
        comment.save()
    }

    fun insertComment(comment: Comment) {
        val dbComment = DBComment(comment.docname, comment.code_id.toString(),
                comment.code_type.toString(), comment.user_photo, comment.user_id.toString(),
                comment.user_nick, comment.content)
        insertComment(dbComment)
    }

    fun insertComment(title: String, comment: ParentComment) {
        val dbComment = DBComment(title, comment.code_id,
                comment.code_type, comment.user_photo, comment.user_id.toString(),
                comment.user_nick, comment.content)
        insertComment(dbComment)
    }

    fun insertComment(title: String, comment: com.lzx.guanchajava.POJO.bean.userInfo.replied.ParentComment) {
        val dbComment = DBComment(title, comment.code_id,
                comment.code_type, comment.user_photo, comment.user_id.toString(),
                comment.user_nick, comment.content)
        insertComment(dbComment)
    }

    fun insertComment(comment: com.lzx.guanchajava.POJO.bean.userInfo.replied.Comment) {
        val dbComment = DBComment(comment.docname, comment.code_id.toString(),
                comment.code_type.toString(), comment.user_photo, comment.user_id.toString(),
                comment.user_nick, comment.content)
        insertComment(dbComment)
    }

    fun insertComment(title: String, comment: Reply) {
        val dbComment = DBComment(title, comment.code_id.toString(),
                comment.code_type, comment.user_photo, comment.user_id.toString(),
                comment.user_nick, comment.content)
        insertComment(dbComment)
    }

    fun deleteComment(comment: DBComment) {
        comment.delete()
    }

    fun queryComments() = LitePal.findAll<DBComment>()

}
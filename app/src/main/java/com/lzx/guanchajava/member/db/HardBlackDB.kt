package com.lzx.guanchajava.member.db

/*import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener*/
import com.lzx.guanchajava.member.bean.DBHardBlack
import org.litepal.LitePal
import org.litepal.extension.find
import org.litepal.extension.findAll

/**
 * 超级黑名单工具类(在adapter中侵入)
 * 可选形式：
 * 1.在评论列表中替换黑名单中人的评论为指定文字，点击之后才显示原文字
 * 2.完全隐藏
 */
object HardBlackDB {

    fun saveUser(user: DBHardBlack) {
        user.save()
    }
    fun queryAllDB() = LitePal.findAll<DBHardBlack>()

    fun queryAllUid() : List<String> {
        var result = mutableListOf<String>()
        for (x in queryAllDB()) {
            result.add(x.user_id)
        }
        return result
    }

    fun checkExit(user: DBHardBlack) = LitePal.where("user_id = ?", user.user_id).find<DBHardBlack>().isNotEmpty()

    fun deleteDB(user: DBHardBlack) {
        user.delete()
    }
}
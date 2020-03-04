package com.lzx.guanchajava.adapter.replyAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import cn.droidlover.xrichtext.XRichText
import com.lzx.guanchajava.POJO.bean.reply.BaseReply
import com.lzx.guanchajava.POJO.bean.reply.Reply
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.UserDetailActivity
import com.lzx.guanchajava.member.db.CCDB
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.util.CommentUtil
import com.lzx.guanchajava.util.PopupUtils
import com.lzx.guanchajava.util.RichTextUtis
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class TypeReply(val context: Context, val fragmentManager: FragmentManager, override var title: String, val blackedUidList: List<String>) : IReply {

    override fun isForViewType(reply: BaseReply): Boolean {
        return reply.status == 2 || reply.status == 3 || reply.status == 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_reply, parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, r: BaseReply) {
        val h = holder as Holder
        val reply = r as Reply
        if (reply.user_id.toString() in blackedUidList) {
            holder.name.apply {
                visibility = View.VISIBLE
                text = "该评论已被屏蔽,点击显示"
                onClick {
                    setReply(h, reply)
                }
            }
        } else {
            setReply(h, reply)
        }

        if (reply.parent_id != 0 && !reply.parent.isNullOrEmpty()) {
            val parentReply = reply.parent[0]
            if (reply.parent_id.toString() in blackedUidList) {
                holder.parent_name.apply {
                    visibility = View.VISIBLE
                    text = "该评论已被屏蔽,点击显示"
                    onClick { setParentReply(h, parentReply) }
                }
            } else {
                setParentReply(h, parentReply)
            }
        } else {
            holder.parent.visibility = ViewGroup.GONE
        }
    }

    private fun setReply(holder: Holder, reply: Reply) {
        holder.pic.apply {
            visibility = View.VISIBLE
            ifCircle = true
            url = reply.user_photo
            onClick { goToUserDetail(reply.user_id.toString()) }
        }
        holder.name.apply {
            visibility = View.VISIBLE
            text = reply.user_nick
            onClick { goToUserDetail(reply.user_id.toString()) }
        }
        holder.time.apply {
            visibility = View.VISIBLE
            text = reply.created_at
        }

        RichTextUtis(context).showText(holder.content, reply.content)

        if (reply.reply_count != 0) {
            holder.more.visibility = ViewGroup.VISIBLE
            holder.more.text = "共${reply.reply_count}条回复"
            holder.more.onClick { CommentUtil.showChild(reply.id.toString(), fragmentManager, title) }
        } else {
            holder.more.visibility = ViewGroup.GONE
        }

        holder.comment_btn.apply {
            visibility = View.VISIBLE
            onClick { CommentUtil.publishComment(reply.id.toString(), reply.code_type, reply.code_id, reply.user_nick, fragmentManager) }
        }

        holder.praise_btn.apply {
            visibility = View.VISIBLE
            text = reply.praise_num.toString()
            onClick { CommentUtil.praiseComment(reply.id.toString(), holder.praise_btn) }
        }
        if (reply.has_praise) CommentUtil.setImageDrawable(holder.praise_btn, R.drawable.ic_praise_fill_12dp)
        else CommentUtil.setImageDrawable(holder.praise_btn, R.drawable.ic_praise_12dp)


        holder.tread_btn.apply {
            visibility = View.VISIBLE
            text = reply.tread_num
            onClick { CommentUtil.treadComment(reply.id.toString() , holder.tread_btn) }
        }
        if (reply.has_tread) CommentUtil.setImageDrawable(holder.tread_btn, R.drawable.ic_tread_fill_12dp)
        else CommentUtil.setImageDrawable(holder.tread_btn, R.drawable.ic_tread_12dp)

        holder.menu.apply {
            visibility = View.VISIBLE
            onClick {
                PopupUtils.popupComment(holder.itemView) {
                    CCDB.insertComment(title, reply)
                }
            }
        }
    }

    private fun setParentReply(holder: Holder, reply: Reply) {
        holder.parent.visibility = ViewGroup.VISIBLE

        holder.parent_time.apply {
            visibility = View.VISIBLE
            text = reply.created_at
        }
        holder.parent_name.apply {
            visibility = View.VISIBLE
            text = reply.user_nick
            onClick { goToUserDetail(reply.user_id.toString()) }
        }

        holder.parent_more.apply {
            visibility = View.VISIBLE
            text = "共${reply.reply_count}条回复"
        }
        holder.parent_more.apply {
            visibility = View.VISIBLE
            onClick { CommentUtil.showChild(reply.id.toString(), fragmentManager, title) }
        }

        RichTextUtis(context).showText(holder.parent_content, reply.content)

        holder.parent_comment_btn.apply {
            visibility = View.VISIBLE
            onClick { CommentUtil.publishComment(reply.id.toString(), reply.code_type, reply.code_id, reply.user_nick, fragmentManager) }
        }

        holder.parent_praise_btn.apply {
            visibility = View.VISIBLE
            text = reply.praise_num.toString()
            onClick { CommentUtil.praiseComment(reply.id.toString(), holder.parent_praise_btn) }
        }
        if (reply.has_praise) CommentUtil.setImageDrawable(holder.parent_praise_btn, R.drawable.ic_praise_fill_12dp)
        else CommentUtil.setImageDrawable(holder.parent_praise_btn, R.drawable.ic_praise_12dp)

        holder.parent_tread_btn.apply {
            visibility = View.VISIBLE
            text = reply.tread_num
            onClick { CommentUtil.treadComment(reply.id.toString(), holder.parent_tread_btn) }
        }
        if (reply.has_tread) CommentUtil.setImageDrawable(holder.parent_tread_btn, R.drawable.ic_tread_fill_12dp)
        else CommentUtil.setImageDrawable(holder.parent_tread_btn, R.drawable.ic_tread_12dp)

        holder.parent_menu.apply {
            visibility = View.VISIBLE
            onClick {
                PopupUtils.popupComment(holder.itemView) {
                    CCDB.insertComment(title, reply)
                }
            }
        }
    }

    /**
     * 设置超级黑名单的内容
     * @param view XRichText
     * @param type Int      该回复的种类：1直接回复； 2parent reply
     * @param holder Holder
     * @param reply Reply
     */
    private fun setHardBlack(view: XRichText, type: Int, holder: Holder, reply: Reply) {
        view.apply {
            text("该用户已被加入超级黑名单, 点击显示内容")
            onClick {
                if (type == 1) setReply(holder, reply)
                else setParentReply(holder, reply)
            }

        }
    }

    private fun goToUserDetail(id: String) {
        App.context.startActivity<UserDetailActivity>("UID" to id)
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val comment_btn = view.findViewById<ImageButton>(R.id.reply_comment_btn)
        val tread_btn = view.findViewById<TextView>(R.id.reply_tread_btn)
        val pic = view.findViewById<UrlImageView>(R.id.reply_pic)
        val name = view.findViewById<TextView>(R.id.reply_name)
        val content = view.findViewById<XRichText>(R.id.reply_content)
        val time = view.findViewById<TextView>(R.id.reply_time)
        val more = view.findViewById<TextView>(R.id.reply_more)
        val praise_btn = view.findViewById<TextView>(R.id.reply_praise_btn)
        val menu = view.find<ImageButton>(R.id.reply_menu)

        val parent = view.findViewById<ConstraintLayout>(R.id.reply_parent)
        val parent_time = view.findViewById<TextView>(R.id.reply_parent_time)
        val parent_more = view.findViewById<TextView>(R.id.reply_parent_more)
        val parent_comment_btn = view.findViewById<ImageButton>(R.id.reply_parent_comment_btn)
        val parent_tread_btn = view.findViewById<TextView>(R.id.reply_parent_tread_btn)
        val parent_praise_btn = view.findViewById<TextView>(R.id.reply_parent_praise_btn)
        val parent_name = view.findViewById<TextView>(R.id.reply_parent_name)
        val parent_content = view.findViewById<XRichText>(R.id.reply_parent_content)
        val parent_menu = view.find<ImageButton>(R.id.reply_parent_menu)
    }
}
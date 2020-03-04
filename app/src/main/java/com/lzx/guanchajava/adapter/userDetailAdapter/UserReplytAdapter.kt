package com.lzx.guanchajava.adapter.userDetailAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import cn.droidlover.xrichtext.XRichText
import com.lzx.guanchajava.POJO.bean.userDetail.reply.Comment
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.fragment.reply.ChildReplyFragment
import com.lzx.guanchajava.member.db.CCDB
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.util.CommentUtil
import com.lzx.guanchajava.util.PopupUtils
import com.lzx.guanchajava.util.RichTextUtis
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * 用户发表里的回复
 */
class UserReplytAdapter(val fragmentManager: FragmentManager) : RecyclerView.Adapter<UserReplytAdapter.Holder>(){

    var data = mutableListOf<Comment>()
    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_user_reply, p0, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val d = data[p1]


        p0.time.text = d.created_at
        if (d.reply_count != 0) {
            p0.more.apply {
                visibility = View.VISIBLE
                onClick { loadReply(d.id.toString()) }
            }
        } else {
            p0.more.visibility = View.GONE
        }
        p0.reply_btn.onClick {
            CommentUtil.publishComment(d.id.toString(), d.code_type.toString(), d.code_id.toString(), d.user_nick, fragmentManager)
        }
        p0.tread_num.apply {
            text = d.tread_num
            onClick { CommentUtil.treadComment(d.id.toString(), p0.tread_num) }
        }
        p0.praise_num.apply {
            text = d.praise_num1
            onClick { CommentUtil.praiseComment(d.id.toString(), p0.praise_num) }
        }

        p0.menu.onClick {
            PopupUtils.popupComment(p0.itemView) {
                CCDB.insertComment(d)
            }
        }

        if (d.has_praise) setImageDrawable(p0.praise_num, R.drawable.ic_praise_fill_12dp)
        else {setImageDrawable(p0.praise_num, R.drawable.ic_praise_12dp)}
        if (d.has_tread) setImageDrawable(p0.tread_num, R.drawable.ic_tread_fill_12dp)
        else {setImageDrawable(p0.tread_num, R.drawable.ic_tread_12dp)}

        RichTextUtis(p0.itemView.context).showText(p0.content, d.content)

        if (d.parent_comment != null) {
            val p = d.parent_comment
            p0.parent.visibility = View.VISIBLE
            p0.parent_reply_btn.onClick {
                CommentUtil.publishComment(p.id.toString(), p.code_type, p.code_id, p.user_nick, fragmentManager)
            }
            p0.parent_tread_num.apply {
                text = p.tread_num
                onClick { CommentUtil.treadComment(p.id.toString(), p0.parent_tread_num) }
            }
            p0.parent_praise_num.apply {
                text = p.praise_num1
                onClick { CommentUtil.praiseComment(p.id.toString(), p0.parent_praise_num) }
            }
            p0.parent_user.text = p.user_nick
            RichTextUtis(p0.itemView.context).showText(p0.parent_content, p.content)
            p0.parent_time.text = p.created_at
            p0.parent_more.onClick { loadReply(p.id.toString()) }

            if (p.has_praise) setImageDrawable(p0.parent_praise_num, R.drawable.ic_praise_fill_12dp)
            else {setImageDrawable(p0.parent_praise_num, R.drawable.ic_praise_12dp)}
            if (p.has_tread) setImageDrawable(p0.parent_tread_num, R.drawable.ic_tread_fill_12dp)
            else {setImageDrawable(p0.parent_tread_num, R.drawable.ic_tread_12dp)}

            p0.parent_menu.onClick {
                PopupUtils.popupComment(p0.itemView) {
                    CCDB.insertComment(d.docname, p)
                }
            }
        } else {
            p0.parent.visibility = View.GONE
        }


        p0.title.apply {
            text = d.docname
            onClick {
                onClickListener.onClick(d.code_id.toString(), d.code_type.toString())
            }
        }
    }

    private fun setImageDrawable(btn: TextView, @DrawableRes id: Int) {
        val b = App.context.getDrawable(id)
        btn.setCompoundDrawablesWithIntrinsicBounds(b, null, null, null)
    }

    private fun increaseNum(num: TextView) {
        val n = num.text.toString().toInt()
        num.text = (n + 1).toString()
    }

    private fun decreaseNum(num: TextView) {
        val n = num.text.toString().toInt()
        num.text = (n - 1).toString()
    }

    private fun loadReply(id: String) {
        ChildReplyFragment.newInstance(id, data[0]?.docname).show(fragmentManager, "CHILDREPLY")
    }

    interface OnClickListener {
        fun onClick(id: String, type: String)
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view){
        val time = view.findViewById<TextView>(R.id.reply_time)
        val more = view.findViewById<TextView>(R.id.reply_more)
        val reply_btn = view.findViewById<ImageButton>(R.id.reply_reply_btn)
        val tread_num = view.findViewById<TextView>(R.id.reply_tread_num)
        val praise_num = view.findViewById<TextView>(R.id.reply_praise_num)
        val title = view.findViewById<TextView>(R.id.reply_title)
        val content = view.findViewById<XRichText>(R.id.reply_content)
        val menu = view.find<ImageButton>(R.id.reply_menu)

        val parent = view.findViewById<ConstraintLayout>(R.id.reply_parent)
        val parent_reply_btn = view.findViewById<ImageButton>(R.id.reply_parent_reply_btn)
        val parent_tread_num = view.findViewById<TextView>(R.id.reply_parent_tread_num)
        val parent_user = view.findViewById<TextView>(R.id.reply_parent_user)
        val parent_content = view.findViewById<XRichText>(R.id.reply_parent_content)
        val parent_time = view.findViewById<TextView>(R.id.reply_parent_time)
        val parent_praise_num = view.findViewById<TextView>(R.id.reply_parent_praise_num)
        val parent_more = view.findViewById<TextView>(R.id.reply_parent_more)
        val parent_menu = view.find<ImageButton>(R.id.reply_parent_menu)
    }
}
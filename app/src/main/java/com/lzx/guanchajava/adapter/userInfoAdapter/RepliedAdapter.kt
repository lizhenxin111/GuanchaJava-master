package com.lzx.guanchajava.adapter.userInfoAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import cn.droidlover.xrichtext.XRichText
import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.operate.praiseComment.PraiseComment
import com.lzx.guanchajava.POJO.bean.operate.treadComment.TreadComment
import com.lzx.guanchajava.POJO.bean.userInfo.replied.Comment
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.UserDetailActivity
import com.lzx.guanchajava.view.fragment.reply.ChildReplyFragment
import com.lzx.guanchajava.view.fragment.reply.CommentFragment
import com.lzx.guanchajava.member.db.CCDB
import com.lzx.guanchajava.util.*
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.find
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * 通知里的回复
 */
class RepliedAdapter(val fragmentManager: FragmentManager) : RecyclerView.Adapter<RepliedAdapter.Holder>() {

    var data = mutableListOf<Comment>()
    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_reply, p0, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val h = p0
        val reply = data[p1]

        h.title.apply {
            visibility = View.VISIBLE
            text = reply.docname
            onClick {
                onClickListener.onClick(reply.code_id, reply.code_type)
            }
        }

        h.pic.apply {
            url = reply.user_photo
            onClick { goToUserDetail(reply.user_id.toString()) }
        }
        h.name.apply {
            text = reply.user_nick
            onClick { goToUserDetail(reply.user_id.toString()) }
        }
        h.time.text = reply.created_at

        RichTextUtis(h.itemView.context).showText(h.content, reply.content)

        if (reply.reply_count != 0) {
            h.more.visibility = ViewGroup.VISIBLE
            h.more.onClick { showChild(reply.id.toString()) }
        } else {
            h.more.visibility = ViewGroup.GONE
        }

        h.comment_btn.onClick {
            CommentUtil.publishComment(reply.id.toString(), reply.code_type.toString(), reply.code_id.toString(), reply.user_nick, fragmentManager)
        }

        h.praise_btn.text = reply.praise_num1
        h.praise_btn.onClick { praiseComment(reply.id.toString(), h.praise_btn) }
        if (reply.has_praise) setImageDrawable(h.praise_btn, R.drawable.ic_praise_fill_12dp)
        else {setImageDrawable(h.praise_btn, R.drawable.ic_praise_12dp)}


        h.tread_btn.text = reply.tread_num
        h.tread_btn.onClick { treadComment(reply.id.toString(), h.tread_btn) }
        if (reply.has_tread) setImageDrawable(h.tread_btn, R.drawable.ic_tread_fill_12dp)
        else {setImageDrawable(h.tread_btn, R.drawable.ic_tread_12dp)}

        h.menu.onClick {
            PopupUtils.popupComment(h.itemView) {
                CCDB.insertComment(reply)
            }
        }

        val parent = reply.parent_comment
        if (parent != null) {
            h.parent.visibility = ViewGroup.VISIBLE

            h.parent_time.text = parent.created_at
            h.parent_name.apply {
                text = parent.user_nick
                onClick { goToUserDetail(parent.user_id.toString()) }
            }
            h.parent_more.onClick { showChild(parent.id.toString()) }

            RichTextUtis(h.itemView.context).showText(h.parent_content, parent.content)

            h.parent_comment_btn.onClick {
                CommentUtil.publishComment(parent.id.toString(), parent.code_type, parent.code_id, parent.user_nick, fragmentManager)
            }

            h.parent_praise_btn.text = parent.praise_num1
            h.parent_praise_btn.onClick { praiseComment(parent.id.toString(), h.parent_praise_btn) }

            h.parent_tread_btn.text = parent.tread_num
            h.parent_tread_btn.onClick { treadComment(parent.id.toString(), h.parent_tread_btn) }
            if (parent.has_praise) setImageDrawable(h.parent_praise_btn, R.drawable.ic_praise_fill_12dp)
            else setImageDrawable(h.parent_praise_btn, R.drawable.ic_praise_12dp)
            if (parent.has_tread) setImageDrawable(h.parent_tread_btn, R.drawable.ic_tread_fill_12dp)
            else setImageDrawable(h.parent_tread_btn, R.drawable.ic_tread_12dp)

            h.parent_menu.onClick {
                PopupUtils.popupComment(h.itemView) {
                    CCDB.insertComment(reply.docname, parent)
                }
            }
        } else {
            h.parent.visibility = ViewGroup.GONE
        }

    }

    private fun goToUserDetail(id: String) {
        App.context.startActivity<UserDetailActivity>("UID" to id)
    }

    private fun publishComment(type: String, codeId: String, parentId: String, name: String) {
        CommentFragment.newInstance(type, codeId, parentId, name)
    }

    private fun praiseComment(commentId: String, btn: TextView) {
        if (!LoginUtil.hasToken()) {
            Toast.makeText(App.context, "请登录", Toast.LENGTH_SHORT).show()
            return
        }
        Fuel.post(Api.praiseComment(), listOf("comment_id" to commentId, "from" to "cms")).responseObject(PraiseComment.Deserializer()) { _, response, result ->
            val (data, error) = result
            if (error != null) {
                App.context.longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val r = result.get().data.is_praise
                if (r) {
                    Toast.makeText(App.context, "赞一下", Toast.LENGTH_SHORT).show()
                    setImageDrawable(btn, R.drawable.ic_praise_fill_12dp)
                    increaseNum(btn)
                } else {
                    Toast.makeText(App.context, "不赞了", Toast.LENGTH_SHORT).show()
                    setImageDrawable(btn, R.drawable.ic_praise_12dp)
                    decreaseNum(btn)
                }
            }
        }
    }

    private fun treadComment(commentId: String, btn: TextView) {
        if (!LoginUtil.hasToken()) {
            Toast.makeText(App.context, "请登录", Toast.LENGTH_SHORT).show()
            return
        }
        Fuel.post(Api.treadComment(), listOf("id" to commentId, "from" to "cms")).responseObject(TreadComment.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                App.context.longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val r = result.get().data.is_tread
                if (r) {
                    Toast.makeText(App.context, "踩一下", Toast.LENGTH_SHORT).show()
                    setImageDrawable(btn, R.drawable.ic_tread_fill_12dp)
                    increaseNum(btn)
                } else {
                    Toast.makeText(App.context, "不踩了", Toast.LENGTH_SHORT).show()
                    setImageDrawable(btn, R.drawable.ic_tread_12dp)
                    decreaseNum(btn)
                }
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

    private fun showChild(childId: String) {
        ChildReplyFragment.newInstance(childId, data[0]?.docname).show(fragmentManager, "CHILDREPLY")
    }


    interface OnClickListener {
        fun onClick(id: Int, type: Int)
    }      //type=1:新闻；type=2:风闻

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.find<TextView>(R.id.reply_title)
        val pic = view.find<UrlImageView>(R.id.reply_pic)
        val name = view.find<TextView>(R.id.reply_name)
        val content = view.find<XRichText>(R.id.reply_content)
        val time = view.find<TextView>(R.id.reply_time)
        val more = view.find<TextView>(R.id.reply_more)
        val comment_btn = view.find<ImageButton>(R.id.reply_comment_btn)
        val tread_btn = view.find<TextView>(R.id.reply_tread_btn)
        val praise_btn = view.find<TextView>(R.id.reply_praise_btn)
        val menu = view.find<ImageButton>(R.id.reply_menu)

        val parent = view.find<ConstraintLayout>(R.id.reply_parent)
        val parent_time = view.find<TextView>(R.id.reply_parent_time)
        val parent_more = view.find<TextView>(R.id.reply_parent_more)
        val parent_comment_btn = view.find<ImageButton>(R.id.reply_parent_comment_btn)
        val parent_tread_btn = view.find<TextView>(R.id.reply_parent_tread_btn)
        val parent_praise_btn = view.find<TextView>(R.id.reply_parent_praise_btn)
        val parent_name = view.find<TextView>(R.id.reply_parent_name)
        val parent_content = view.find<XRichText>(R.id.reply_parent_content)
        val parent_menu = view.find<ImageButton>(R.id.reply_parent_menu)

        val constainer = view.find<ConstraintLayout>(R.id.replied_container)
    }
}
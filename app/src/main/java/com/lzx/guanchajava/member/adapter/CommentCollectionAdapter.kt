package com.lzx.guanchajava.member.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import cn.droidlover.xrichtext.XRichText
import com.lzx.guanchajava.R
import com.lzx.guanchajava.view.activity.UserDetailActivity
import com.lzx.guanchajava.member.bean.DBComment
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.view.widget.UrlImageView
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import org.jetbrains.anko.startActivity

/**
 * 展示收藏评论的Adapter
 * @property fragmentManager FragmentManager
 * @property onClickListener OnClickListener
 * @property onLongClickListener OnLongClickListener
 * @property data MutableList<DBComment>
 * @constructor
 */
class CommentCollectionAdapter(val fragmentManager: FragmentManager, val onClickListener: OnClickListener, val onLongClickListener: OnLongClickListener) : RecyclerView.Adapter<CommentCollectionAdapter.Holder>() {

    var data = mutableListOf<DBComment>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(p0.context).inflate(R.layout.item_reply, p0, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        val h = p0
        val reply = data[p1]

        h.title.apply {
            visibility = View.VISIBLE
            text = reply.title
            onClick {
                onClickListener.onClick(reply.code_id.toInt(), reply.code_type.toInt())
            }
        }

        h.pic.apply {
            ifCircle = true
            url = reply.user_photo
            onClick { goToUserDetail(reply.user_id) }
        }
        h.name.apply {
            text = reply.user_nick
            onClick { goToUserDetail(reply.user_id) }
        }
        h.time.visibility = View.GONE

        h.content.apply {
            setText(reply.content)
            onLongClick {
                onLongClickListener.onLongClick(h.itemView, reply, p1)
            }
        }

        h.itemView.onLongClick {
            onLongClickListener.onLongClick(h.itemView, reply, p1)
        }

        h.praise_btn.visibility = View.GONE
        h.tread_btn.visibility = View.GONE
        h.parent.visibility = ViewGroup.GONE
        h.comment.visibility = ViewGroup.GONE
        h.menu.visibility = View.GONE

    }

    private fun goToUserDetail(id: String) {
        App.context.startActivity<UserDetailActivity>("UID" to id)
    }



    interface OnClickListener {
        fun onClick(id: Int, type: Int)
    }      //type=1:新闻；type=2:风闻
    interface OnLongClickListener {
        fun onLongClick(view: View, comment: DBComment, position: Int)
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.find<TextView>(R.id.reply_title)
        val pic = view.find<UrlImageView>(R.id.reply_pic)
        val name = view.find<TextView>(R.id.reply_name)
        val content = view.find<XRichText>(R.id.reply_content)
        val time = view.find<TextView>(R.id.reply_time)
        val tread_btn = view.find<TextView>(R.id.reply_tread_btn)
        val praise_btn = view.find<TextView>(R.id.reply_praise_btn)
        val comment = view.find<ImageButton>(R.id.reply_comment_btn)
        val parent = view.find<ConstraintLayout>(R.id.reply_parent)
        val constainer = view.find<ConstraintLayout>(R.id.replied_container)
        val menu = view.find<ImageButton>(R.id.reply_menu)
    }
}
package com.lzx.guanchajava.view.fragment.reply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.LoginUtil
import com.lzx.guanchajava.util.SaveContent
import com.lzx.guanchajava.viewmodel.reply.vmReply
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast


class CommentFragment : BottomSheetDialogFragment() {

    private val mReplyViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
                .get(vmReply::class.java)
    }

    private lateinit var mParentId: String  //被回复的评论的id
    private lateinit var mType: String      //文章类型
    private lateinit var mCodeId: String    //文章id
    private lateinit var mName: String      //被回复人的昵称

    private var mSaveComment = true

    private lateinit var vContent: EditText

    companion object {
        fun newInstance(articleType: String, articleId: String, parentCommmentId: String, parentUserName: String): CommentFragment = CommentFragment().apply {
            arguments = Bundle().apply {
                putString("PARENTID", parentCommmentId)
                putString("CODEID", articleId)
                putString("TYPE", articleType)
                putString("NAME", parentUserName)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        initProperty()

        val v = inflater.inflate(R.layout.fragment_comment, container, false)
        val close = v.findViewById<ImageButton>(R.id.comment_close)
        vContent = v.findViewById(R.id.comment_content)
        val send = v.findViewById<ImageButton>(R.id.comment_send)
        val title = v.findViewById<TextView>(R.id.comment_title)

        if (SaveContent.hasSavedComment()) vContent.setText(SaveContent.getSavedComment())

        if (mParentId != "0") title.text = "回复@$mName"

        close.onClick { dismiss() }
        send.onClick {
            if (!LoginUtil.hasToken()) needLogin()
            else publishComment(vContent.text.toString())
        }
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mReplyViewModel.postCommentResult.observe(viewLifecycleOwner, Observer {
            if (it.code == 0) {
                Toast.makeText(context, "发表成功", Toast.LENGTH_SHORT).show()
                dismiss()
                mSaveComment = false
            } else {
                Toast.makeText(context, "发表失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()

        val view = view
        view!!.post {
            val parent = view.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            mBottomSheetBehavior = behavior as BottomSheetBehavior<CoordinatorLayout>
            mBottomSheetBehavior!!.setBottomSheetCallback(mBottomSheetBehaviorCallback)
        }
    }

    private var mBottomSheetBehavior: BottomSheetBehavior<*>? = null
    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            //禁止拖拽，
            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                //设置为收缩状态
                mBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mSaveComment) {
            SaveContent.savePostComment(vContent.text.toString())
        }
        SaveContent.setSaveComment(mSaveComment)
    }

    private fun needLogin() {
        toast("请登录")
    }

    private fun publishComment(content: String) {
        mReplyViewModel.postComment(Api.publishComment(), mParentId, mType, mCodeId, content)
    }

    private fun initProperty() {
        mParentId = arguments!!.getString("PARENTID")
        mType = arguments!!.getString("TYPE")
        mCodeId = arguments!!.getString("CODEID")
        mName = arguments!!.getString("NAME")
    }
}
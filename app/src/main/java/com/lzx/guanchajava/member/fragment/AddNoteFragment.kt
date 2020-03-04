package com.lzx.guanchajava.member.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lzx.guanchajava.R
import com.lzx.guanchajava.member.bean.DBNote
import com.lzx.guanchajava.member.db.NoteDB
import com.lzx.guanchajava.view.widget.DialogUtils
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

class AddNoteFragment() : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(articleId: String, articleType: String, articleTitle: String, content: String) = AddNoteFragment().apply {
            arguments = Bundle().apply {
                putString("ARTICLEID", articleId)
                putString("ARTICLETYPE", articleType)
                putString("ARTICLETITLE", articleTitle)
                putString("CONTENT", content)
            }
        }
    }

    /**
     * 被选中的文章内容
     */
    private lateinit var mContent: String
    /**
     * 文章id
     */
    private lateinit var mArticleId :String
    private lateinit var mArticleType: String
    private lateinit var mArticleTitle: String

    private lateinit var add_note_close : ImageButton
    private lateinit var add_note_title : TextView
    private lateinit var add_note_send : ImageButton
    private lateinit var add_note_text_layout : TextInputLayout
    private lateinit var add_note_text : TextInputEditText

    /**
     * 退出方式
     * false:点击空白退出; true:点击按键退出
     */
    private var isChecked = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContent = arguments!!.getString("CONTENT")
        mArticleId = arguments!!.getString("ARTICLEID")
        mArticleType = arguments!!.getString("ARTICLETYPE")
        mArticleTitle = arguments!!.getString("ARTICLETITLE")
        val v = inflater.inflate(R.layout.fragment_add_note, container, false)
        initComponent(v)
        return v
    }

    private fun initComponent(view: View) {
        add_note_close = view.find(R.id.add_note_close)
        add_note_title = view.find(R.id.add_note_title)
        add_note_send = view.find(R.id.add_note_send)
        add_note_text_layout = view.find(R.id.add_note_text_layout)
        add_note_text = view.find(R.id.add_note_text)

        add_note_close.onClick { onCloseClick() }
        add_note_send.onClick { onSendClick() }


        add_note_title.text = mContent
    }

    /**
     * 点击退出按钮的逻辑
     */
    private fun onCloseClick() {
        isChecked = true
        onCloseClickEvent()
    }

    /**
     * 点击退出按钮的事件
     */
    private fun onCloseClickEvent() {
        if (add_note_text.text!!.isEmpty()) dismiss()
        else {
            DialogUtils(context!!).showNormal("", "是否保存当前注释?")
                    .setPositiveButton("保存", DialogInterface.OnClickListener { dialog, which ->
                        onSendClick()
                    })
                    .setNegativeButton("退出", DialogInterface.OnClickListener { dialog, which ->
                        this@AddNoteFragment.dismiss()
                    })
                    .show()
        }
    }

    private fun onSendClick() {
        isChecked = true
        if (add_note_text.text!!.trim().isNotEmpty()) {
            saveNote()
            dismiss()
        } else {
            toast("内容为空")
        }
    }

    private fun saveNote() {
        val note = DBNote(mArticleId, mArticleType, mArticleTitle, mContent, add_note_text.text.toString())
        NoteDB.insertNote(note)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        //处理点击空白退出的情况
        if (!isChecked) {
            onCloseClickEvent()
        }
    }
}
package com.lzx.guanchajava.member.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.lzx.guanchajava.R
import com.lzx.guanchajava.member.adapter.NoteListAdapter
import com.lzx.guanchajava.member.bean.DBNote
import com.lzx.guanchajava.member.db.NoteDB
import com.lzx.guanchajava.view.widget.DialogUtils
import com.lzx.guanchajava.view.widget.EmptyRecyclerView
import com.lzx.guanchajava.view.widget.FixBottomSheetDialogFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk27.coroutines.onClick

class NoteListFragment: FixBottomSheetDialogFragment() {

    companion object {
        fun newInstance(articleId: String) = NoteListFragment().apply {
            arguments = Bundle().apply {
                putString("ARTICLEID", articleId)
            }
        }
    }

    private lateinit var mArticleId: String

    private lateinit var vNoteContainer : ConstraintLayout
    private lateinit var vNoteClose : ImageButton
    private lateinit var vNoteSearch : TextView
    private lateinit var vNoteList : EmptyRecyclerView


    private lateinit var mAdapter : NoteListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mArticleId = arguments!!.getString("ARTICLEID")
        val v = inflater.inflate(R.layout.fragment_note, container, false)
        initComponent(v)
        return v
    }


    override fun onStart() {
        super.onStart()
        initNote()
    }

    private fun initComponent(v : View) {
        vNoteContainer = v.find(R.id.note_container)
        vNoteClose = v.find(R.id.note_close)
        vNoteSearch = v.find(R.id.note_search)
        vNoteList = v.find(R.id.note_list)

        vNoteClose.onClick {
            dismiss()
        }
        mAdapter = NoteListAdapter(onLayoutIconClickListener)
        vNoteList.layoutManager = LinearLayoutManager(context)
        vNoteList.adapter = mAdapter
    }

    private fun initNote() {
        mAdapter.data.clear()
        val datas = NoteDB.queryAllNoteById(mArticleId)
        if (datas.isNotEmpty()) {
            mAdapter.data.addAll(datas)
        } else {
            context!!.longToast("没有笔记哦")
        }
        mAdapter.notifyDataSetChanged()
    }

    private val onLayoutIconClickListener = object : NoteListAdapter.OnLayoutIconClickListener {
        override fun onStartIconClick(position: Int, note: DBNote, newNote: String) {
            note.note = newNote
            NoteDB.insertNote(note)
        }

        override fun onEndIconClick(position: Int, note: DBNote) {
            DialogUtils(this@NoteListFragment.context!!)
                    .showNormal("是否删除该评论?", "")
                    .setPositiveButton("是", DialogInterface.OnClickListener { dialog, which ->
                        NoteDB.deleteNoteByContent(note.content)
                        mAdapter.data.removeAt(position)
                        mAdapter.notifyDataSetChanged()
                    })
                    .setNegativeButton("否", DialogInterface.OnClickListener { dialog, which ->

                    })
                    .show()
        }
    }
}
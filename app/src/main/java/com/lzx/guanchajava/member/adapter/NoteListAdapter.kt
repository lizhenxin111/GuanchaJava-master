package com.lzx.guanchajava.member.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lzx.guanchajava.R
import com.lzx.guanchajava.member.bean.DBNote
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.textChangedListener

class NoteListAdapter(val onLayoutIconClickListener: OnLayoutIconClickListener) : RecyclerView.Adapter<NoteListAdapter.Holder>(){
    var data = mutableListOf<DBNote>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val d = data[position]
        holder.layout.hint = d.content
        holder.input.setText(d.note)
        holder.save.setOnClickListener {
            onLayoutIconClickListener.onStartIconClick(position, d, holder.input.text.toString())
        }
        holder.layout.setEndIconOnClickListener {
            onLayoutIconClickListener.onEndIconClick(position, d)
        }
        holder.input.textChangedListener {
            this.afterTextChanged {
                holder.layout.isStartIconVisible = !it!!.toString().equals(d.note)
            }
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val save = view.find<ImageButton>(R.id.item_save)
        val input = view.find<TextInputEditText>(R.id.item_note_input)
        val layout = view.find<TextInputLayout>(R.id.item_note_layout)
    }

    interface OnLayoutIconClickListener {
        fun onEndIconClick(position: Int, note: DBNote)

        fun onStartIconClick(position: Int, note: DBNote, newNote: String)
    }
}
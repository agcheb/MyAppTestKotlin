package com.gb.agcheb.myapptestkotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import kotlinx.android.synthetic.main.item_note.view.*

/**
 * Created by agcheb on 03.02.20.
 */
class NotesRVAdapter : RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {
    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(notes[position])

    override fun getItemCount(): Int = notes.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            // Так делать не гуд, здесь не кешируется getByView -> каждый раз выполняется обращение
            tv_title.text = note.title
            tv_text.text = note.text
            setBackgroundColor(note.color)
        }

    }
}
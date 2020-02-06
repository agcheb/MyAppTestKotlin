package com.gb.agcheb.myapptestkotlin

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import kotlinx.android.synthetic.main.item_note.view.*

/**
 * Created by agcheb on 03.02.20.
 */
class NotesRVAdapter(val onItemViewClick : ((Note) -> Unit)? = null) : RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            // Так делать не гуд, здесь не кешируется getByView -> каждый раз выполняется обращение
            tv_title.text = note.title
            tv_text.text = note.text

            val color = when(note.color) {
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.GREEN -> R.color.green
                Note.Color.BLUE -> R.color.blue
                Note.Color.RED -> R.color.red
                Note.Color.VIOLET -> R.color.violet
                Note.Color.PINK -> R.color.pink
            }

            (this as CardView).setCardBackgroundColor(ContextCompat.getColor(itemView.context, color))
//            setBackgroundColor(color)
            itemView.setOnClickListener {
                onItemViewClick?.invoke(note)
            }
        }

    }
}
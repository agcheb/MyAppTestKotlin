package com.gb.agcheb.myapptestkotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import java.util.*

/**
 * Created by agcheb on 03.02.20.
 */
object NotesRepository {
    private val notesLiveData = MutableLiveData<List<Note>>()
    private val notes: MutableList<Note>
    private val rnd: Random = Random()

    init {
        notes = mutableListOf(
                Note(
                        UUID.randomUUID().toString(),
                        "заметка 1",
                        "текст заметки 1. Не очень длинный, но и так сойдет",
                        Note.Color.WHITE
//                        0xfff06292.toInt()
                ),
                Note(
                        UUID.randomUUID().toString(),
                        "заметка 2",
                        "текст заметки 2. Не очень длинный, но и так сойдет",
                        Note.Color.YELLOW
//                        0xff9575cd.toInt()
                ),
                Note(
                        UUID.randomUUID().toString(),
                        "заметка 3",
                        "текст заметки 3. Не очень длинный, но и так сойдет",
                        Note.Color.GREEN
//                        0xff64b5f6.toInt()
                ),
                Note(
                        UUID.randomUUID().toString(),
                        "заметка 4",
                        "текст заметки 4. Не очень длинный, но и так сойдет",
                        Note.Color.BLUE
//                        0xff4db6ac.toInt()
                ),
                Note(
                        UUID.randomUUID().toString(),
                        "заметка 5",
                        "текст заметки 5. Не очень длинный, но и так сойдет",
                        Note.Color.VIOLET
//                        0xffb2ff59.toInt()
                ),
                Note(
                        UUID.randomUUID().toString(),
                        "заметка 6",
                        "текст заметки 6. Не очень длинный, но и так сойдет",
                        Note.Color.PINK
//                        0xffffeb3b.toInt()
                )
        )
    }

    init {
        notesLiveData.value = notes
    }

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLiveData.value = notes
    }

    fun addOrReplace(note: Note) {
        for (i in notes.indices) {
            if (notes[i] == note) {
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }

    fun getNotes(): LiveData<List<Note>> {
        return notesLiveData
    }

    fun addRandomNote(num: Int) {
        notes.add(Note(
                UUID.randomUUID().toString(),
                "заметка $num",
                "текст заметки $num. Не очень длинный, но и так сойдет",
                Note.Color.WHITE
                //Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        ))
    }
}
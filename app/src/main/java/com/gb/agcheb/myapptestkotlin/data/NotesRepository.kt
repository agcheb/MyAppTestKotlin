package com.gb.agcheb.myapptestkotlin.data

import com.gb.agcheb.myapptestkotlin.data.entity.Note

/**
 * Created by agcheb on 03.02.20.
 */
object NotesRepository {
    private val notes: List<Note>

    init {
        notes = listOf(
                Note(
                        "заметка 1",
                        "текст заметки 1. Не очень длинный, но и так сойдет",
                        0xfff06292.toInt()
                ),
                Note(
                        "заметка 2",
                        "текст заметки 2. Не очень длинный, но и так сойдет",
                        0xff9575cd.toInt()
                ),
                Note(
                        "заметка 3",
                        "текст заметки 3. Не очень длинный, но и так сойдет",
                        0xff64b5f6.toInt()
                ),
                Note(
                        "заметка 4",
                        "текст заметки 4. Не очень длинный, но и так сойдет",
                        0xff4db6ac.toInt()
                ),
                Note(
                        "заметка 5",
                        "текст заметки 5. Не очень длинный, но и так сойдет",
                        0xffb2ff59.toInt()
                ),
                Note(
                        "заметка 6",
                        "текст заметки 6. Не очень длинный, но и так сойдет",
                        0xffffeb3b.toInt()
                )
        )
    }

    fun getNotes(): List<Note> {
        return notes
    }
}
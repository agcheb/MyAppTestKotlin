package com.gb.agcheb.myapptestkotlin.ui.note

import androidx.lifecycle.ViewModel
import com.gb.agcheb.myapptestkotlin.data.NotesRepository
import com.gb.agcheb.myapptestkotlin.data.entity.Note

class NoteViewModel: ViewModel() {
    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesRepository.saveNote(it)
        }
    }
}
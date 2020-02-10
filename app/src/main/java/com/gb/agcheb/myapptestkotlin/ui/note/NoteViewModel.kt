package com.gb.agcheb.myapptestkotlin.ui.note

import androidx.lifecycle.Observer
import com.gb.agcheb.myapptestkotlin.data.NotesRepository
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.data.model.NoteResult
import com.gb.agcheb.myapptestkotlin.ui.base.BaseViewModel

class NoteViewModel: BaseViewModel<Note?, NoteViewState>() {
    private var pendingNote: Note? = null

    init {
        viewStateLiveData.value = NoteViewState()
    }

    fun save(note: Note) {
        pendingNote = note
    }

    fun loadNote(noteId: String) {
        NotesRepository.getNoteById(noteId).observeForever(object : Observer<NoteResult> {
            override fun onChanged(t: NoteResult?) {
                t ?: return
                when(t) {
                    is NoteResult.Success<*> -> {
                        viewStateLiveData.value = NoteViewState(note = t.data as? Note)
                    }
                    is NoteResult.Error -> {
                        viewStateLiveData.value = NoteViewState(error = t.error)
                    }
                }
            }

        })
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesRepository.saveNote(it)
        }
    }
}
package com.gb.agcheb.myapptestkotlin.ui.note

import androidx.annotation.VisibleForTesting
import com.gb.agcheb.myapptestkotlin.data.NotesRepository
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.data.model.NoteResult
import com.gb.agcheb.myapptestkotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class NoteViewModel(private val notesRepository: NotesRepository) : BaseViewModel<NoteData>() {
    private var pendingNote: Note? = null
        get() = getViewState().poll()?.note

    fun save(note: Note) {
        setData(NoteData(note = note))
    }

    fun delete() {

        pendingNote?.let { note ->
            launch {
                try {
                    notesRepository.deleteNote(note.id)
                    setData(NoteData(isDeleted = true))
                } catch (e: Throwable) {
                    setError(e)
                }
            }
        }
    }

    fun loadNote(noteId: String) {
        launch {
            try {
                notesRepository.getNoteById(noteId)?.let {
                    setData(NoteData(note = it))
                }
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }

    @VisibleForTesting
    override public fun onCleared() {
        launch {
            pendingNote?.let {
                try {
                    notesRepository.saveNote(it)
                } catch (e: Throwable) {
                    setError(e)
                }
            }
        }
    }
}
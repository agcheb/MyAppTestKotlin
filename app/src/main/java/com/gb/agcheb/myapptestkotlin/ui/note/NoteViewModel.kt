package com.gb.agcheb.myapptestkotlin.ui.note

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import com.gb.agcheb.myapptestkotlin.data.NotesRepository
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.data.model.NoteResult
import com.gb.agcheb.myapptestkotlin.ui.base.BaseViewModel

class NoteViewModel(private val notesRepository: NotesRepository) : BaseViewModel<NoteViewState.Data?, NoteViewState>() {
    private var pendingNote: Note? = null
        get() = viewStateLiveData.value?.data?.note

    fun save(note: Note) {
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }

    fun delete() {
        pendingNote?.let { notesRepository.deleteNote(it.id).observeForever { result ->
            viewStateLiveData.value = when (result) {
                is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(isDeleted = true))
                is NoteResult.Error -> NoteViewState(error = result.error)
            }
        } }
    }

    fun loadNote(noteId: String) {
        notesRepository.getNoteById(noteId).observeForever { result ->
            result?.let {
                viewStateLiveData.value = when (result) {
                    is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(note = result.data as? Note))
                    is NoteResult.Error -> NoteViewState(error = result.error)
                }
            }
        }
    }

    @VisibleForTesting
    override public fun onCleared() {
        pendingNote?.let {
            notesRepository.saveNote(it)
        }
    }
}
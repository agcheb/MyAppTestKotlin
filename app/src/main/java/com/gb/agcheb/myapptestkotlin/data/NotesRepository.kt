package com.gb.agcheb.myapptestkotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.data.model.NoteResult
import com.gb.agcheb.myapptestkotlin.data.provider.FireStoreProvider
import com.gb.agcheb.myapptestkotlin.data.provider.RemoteDataProvider
import java.util.*

/**
 * Created by agcheb on 03.02.20.
 */
class NotesRepository(val remoteDataProvider: RemoteDataProvider) {
    fun getNotes() = remoteDataProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)
    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
    fun getCurrentUser() = remoteDataProvider.getCurrentUser()
    fun deleteNote(id: String) = remoteDataProvider.deleteNote(id)
}
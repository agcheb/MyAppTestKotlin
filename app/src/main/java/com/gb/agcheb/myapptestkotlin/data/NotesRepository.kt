package com.gb.agcheb.myapptestkotlin.data

import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.data.provider.RemoteDataProvider
import java.util.*

/**
 * Created by agcheb on 03.02.20.
 */
class NotesRepository(val remoteDataProvider: RemoteDataProvider) {
    fun getNotes() = remoteDataProvider.subscribeToAllNotes()
    suspend fun saveNote(note: Note) = remoteDataProvider.saveNote(note)
    suspend fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
    suspend fun getCurrentUser() = remoteDataProvider.getCurrentUser()
    suspend fun deleteNote(id: String) = remoteDataProvider.deleteNote(id)
}
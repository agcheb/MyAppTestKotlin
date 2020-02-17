package com.gb.agcheb.myapptestkotlin.data.provider

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.data.entity.User
import com.gb.agcheb.myapptestkotlin.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
    fun getCurrentUser():LiveData<User?>
}
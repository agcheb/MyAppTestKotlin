package com.gb.agcheb.myapptestkotlin.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.data.entity.User
import com.gb.agcheb.myapptestkotlin.data.exceptions.NoAuthException
import com.gb.agcheb.myapptestkotlin.data.model.NoteResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreProvider(private val firebaseAuth: FirebaseAuth, private val store: FirebaseFirestore) : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val currentUser
        get() = firebaseAuth.currentUser


    private val getUserNotesCollection: CollectionReference
        get() =
            currentUser?.let {
                store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
            } ?: throw NoAuthException()

    override fun getCurrentUser() = MutableLiveData<User?>().apply {
        value = currentUser?.let {
            User(it.displayName ?: "", it.email ?: "")
        }
    }

    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply {

        try {
            getUserNotesCollection.addSnapshotListener { snapshot, e ->
                e?.let {
                    throw it
                } ?: let {
                    snapshot?.let { snapshot ->
                        value = NoteResult.Success(snapshot.documents.map { it.toObject(Note::class.java) })
                    }
                }
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }

    }

    override fun getNoteById(id: String) = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection.document(id).get()
                    .addOnSuccessListener { snapshot ->
                        NoteResult.Success(snapshot.toObject(Note::class.java))
                    }.addOnFailureListener { value = NoteResult.Error(it) }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun saveNote(note: Note) = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection.document(note.id).set(note)
                    .addOnSuccessListener {
                        NoteResult.Success(note)
                    }.addOnFailureListener { value = NoteResult.Error(it)}
        } catch (e:Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun deleteNote(noteId: String) = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection.document(noteId).delete()
                    .addOnSuccessListener {
                        NoteResult.Success(null)
                    }.addOnFailureListener { value = NoteResult.Error(it)}
        } catch (e:Throwable) {
            value = NoteResult.Error(e)
        }
    }

}
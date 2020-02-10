package com.gb.agcheb.myapptestkotlin.data.model

import androidx.lifecycle.LiveData

sealed class NoteResult {
    data class Success<out T>(val data: T): NoteResult()
    data class Error(val error: Throwable): NoteResult()
}
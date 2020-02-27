package com.gb.agcheb.myapptestkotlin.ui.note

import com.gb.agcheb.myapptestkotlin.data.entity.Note

data class NoteData(val isDeleted: Boolean = false, val note: Note? = null)
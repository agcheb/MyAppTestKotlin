package com.gb.agcheb.myapptestkotlin.ui.note

import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.ui.base.BaseViewState

class NoteViewState(data: Data = Data(), error: Throwable? = null) : BaseViewState<NoteViewState.Data?>(data, error) {
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}
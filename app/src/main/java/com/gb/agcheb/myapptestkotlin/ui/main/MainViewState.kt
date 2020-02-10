package com.gb.agcheb.myapptestkotlin.ui.main

import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.ui.base.BaseViewState

/**
 * Created by agcheb on 03.02.20.
 */
 class MainViewState(val notes: List<Note>? = null, error: Throwable? = null):BaseViewState<List<Note>?>(notes, error) {

}
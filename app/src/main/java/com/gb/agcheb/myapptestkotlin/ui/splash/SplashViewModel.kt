package com.gb.agcheb.myapptestkotlin.ui.splash

import com.gb.agcheb.myapptestkotlin.data.NotesRepository
import com.gb.agcheb.myapptestkotlin.data.exceptions.NoAuthException
import com.gb.agcheb.myapptestkotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel(private val notesRepository: NotesRepository) : BaseViewModel<Boolean?>() {

    fun requestUser() {
        launch {
            notesRepository.getCurrentUser()?.let {
                setData(true)
            } ?: setError(NoAuthException())
        }
    }
}
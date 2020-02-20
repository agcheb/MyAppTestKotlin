package com.gb.agcheb.myapptestkotlin.ui.splash

import com.gb.agcheb.myapptestkotlin.data.NotesRepository
import com.gb.agcheb.myapptestkotlin.data.exceptions.NoAuthException
import com.gb.agcheb.myapptestkotlin.ui.base.BaseViewModel

class SplashViewModel(private val notesRepository: NotesRepository):BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        notesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(authenticated = true)
            } ?: let { SplashViewState(error = NoAuthException()) }

        }
    }
}
package com.gb.agcheb.myapptestkotlin.ui.splash

import com.gb.agcheb.myapptestkotlin.data.NotesRepository
import com.gb.agcheb.myapptestkotlin.data.exceptions.NoAuthException
import com.gb.agcheb.myapptestkotlin.ui.base.BaseViewModel

class SplashViewModel():BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        NotesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(authenticated = true)
            } ?: let { SplashViewState(error = NoAuthException()) }

        }
    }
}
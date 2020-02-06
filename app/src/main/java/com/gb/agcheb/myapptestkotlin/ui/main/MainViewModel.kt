package com.gb.agcheb.myapptestkotlin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.agcheb.myapptestkotlin.data.NotesRepository

/**
 * Created by agcheb on 30.01.20.
 */
class MainViewModel : ViewModel() {
    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    private var repeatNum = 0;
    init {
        NotesRepository.getNotes().observeForever {
            viewStateLiveData.value = viewStateLiveData.value?.copy(notes = it!!) ?: MainViewState(it!!)
        }

    }

    fun viewState():LiveData<MainViewState> = viewStateLiveData

    fun updateState() {
//        viewStateLiveData.value = "Hello World!! It is " + (++repeatNum) + "try";
        NotesRepository.addRandomNote(++repeatNum);
//        viewStateLiveData.value = viewStateLiveData.value?.copy(notes = it!!) ?: MainViewState(it!!)
//        viewStateLiveData.value = MainViewState(NotesRepository.getNotes())
    }
}
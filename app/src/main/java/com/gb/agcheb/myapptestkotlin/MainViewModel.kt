package com.gb.agcheb.myapptestkotlin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.gb.agcheb.myapptestkotlin.data.NotesRepository

/**
 * Created by agcheb on 30.01.20.
 */
class MainViewModel : ViewModel() {
    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    private var repeatNum = 0;
    init {
        viewStateLiveData.value = MainViewState(NotesRepository.getNotes())
    }

    fun viewState():LiveData<MainViewState> = viewStateLiveData

    fun updateState() {
//        viewStateLiveData.value = "Hello World!! It is " + (++repeatNum) + "try";
        NotesRepository.addRandomNote(++repeatNum);
        viewStateLiveData.value = MainViewState(NotesRepository.getNotes())
    }
}
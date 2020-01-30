package com.gb.agcheb.myapptestkotlin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by agcheb on 30.01.20.
 */
class MainViewModel : ViewModel() {
    private val viewStateLiveData: MutableLiveData<String> = MutableLiveData()

    private var repeatNum = 0;
    init {
        viewStateLiveData.value = "Hello World"
    }

    fun viewState():LiveData<String> = viewStateLiveData

    fun updateHello() {
        viewStateLiveData.value = "Hello World!! It is " + (++repeatNum) + "try";
    }
}
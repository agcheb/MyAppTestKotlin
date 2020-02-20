package com.gb.agcheb.myapptestkotlin

import android.app.Application
import com.gb.agcheb.myapptestkotlin.di.appModule
import com.gb.agcheb.myapptestkotlin.di.mainModule
import com.gb.agcheb.myapptestkotlin.di.noteModule
import com.gb.agcheb.myapptestkotlin.di.splashModule
import org.koin.android.ext.android.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, mainModule, noteModule, splashModule))
    }
}
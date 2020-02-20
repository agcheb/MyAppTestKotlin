package com.gb.agcheb.myapptestkotlin.di

import com.gb.agcheb.myapptestkotlin.data.NotesRepository
import com.gb.agcheb.myapptestkotlin.data.provider.FireStoreProvider
import com.gb.agcheb.myapptestkotlin.data.provider.RemoteDataProvider
import com.gb.agcheb.myapptestkotlin.ui.main.MainViewModel
import com.gb.agcheb.myapptestkotlin.ui.note.NoteViewModel
import com.gb.agcheb.myapptestkotlin.ui.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
//    single<RemoteDataProvider> {FireStoreProvider(get(), get())} можно и так
    single{FireStoreProvider(get(), get())} bind  RemoteDataProvider::class
    single { NotesRepository(get()) }
}

val splashModule = module {
    viewModel {SplashViewModel(get()) }
}

val mainModule = module {
    viewModel {MainViewModel(get()) }
}

val noteModule = module {
    viewModel {NoteViewModel(get()) }
}
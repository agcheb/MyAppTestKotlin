package com.gb.agcheb.myapptestkotlin.ui.main

import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.lifecycle.ViewModelProvider
import com.gb.agcheb.myapptestkotlin.NotesRVAdapter
import com.gb.agcheb.myapptestkotlin.R
import com.gb.agcheb.myapptestkotlin.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter {
            NoteActivity.start(this, it)
        }
        rv_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer {
            it?.let { adapter.notes = it.notes }

        })

        fab.setOnClickListener {
            NoteActivity.start(this)
        }
//        btn_new_try.setOnClickListener {
//            viewModel.updateState()
//            Toast.makeText(this, "Look! I've added smth!", Toast.LENGTH_SHORT).show()
//        }
    }
}

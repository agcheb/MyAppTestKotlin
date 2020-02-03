package com.gb.agcheb.myapptestkotlin

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter()
        rv_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer {
            //            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            it?.let { adapter.notes = it.notes }

        })

        btn_new_try.setOnClickListener {
            viewModel.updateState()
            Toast.makeText(this, "Look! I've added smth!", Toast.LENGTH_SHORT).show()
        }
    }
}

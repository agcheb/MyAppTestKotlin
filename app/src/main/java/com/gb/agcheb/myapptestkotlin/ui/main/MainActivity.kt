package com.gb.agcheb.myapptestkotlin.ui.main

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.gb.agcheb.myapptestkotlin.NotesRVAdapter
import com.gb.agcheb.myapptestkotlin.R
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.ui.base.BaseActivity
import com.gb.agcheb.myapptestkotlin.ui.note.NoteActivity
import com.gb.agcheb.myapptestkotlin.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<List<Note>?, MainViewState>(), LogoutDialog.LogoutListener {

    companion object {
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override val layoutRes = R.layout.activity_main
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rv_notes.layoutManager = GridLayoutManager(this, 2)

        adapter = NotesRVAdapter { note ->
            NoteActivity.start(this, note)
        }

        rv_notes.adapter = adapter

        fab.setOnClickListener {
            NoteActivity.start(this)
        }

//        btn_new_try.setOnClickListener {
//            viewModel.updateState()
//            Toast.makeText(this, "Look! I've added smth!", Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) = MenuInflater(this).inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.logout -> showLogoutDialog()?.let { true }
        else -> false
    }

    private fun showLogoutDialog() {
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?: LogoutDialog.createInstance().show(supportFragmentManager, LogoutDialog.TAG)
    }

    override fun onLogout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                }
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }
}

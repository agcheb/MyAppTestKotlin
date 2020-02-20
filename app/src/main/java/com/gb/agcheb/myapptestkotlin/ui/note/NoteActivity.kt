package com.gb.agcheb.myapptestkotlin.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.gb.agcheb.myapptestkotlin.R
import com.gb.agcheb.myapptestkotlin.commons.getColorInt
import com.gb.agcheb.myapptestkotlin.commons.getColorRes
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_note.toolbar as toolbar1

class NoteActivity : BaseActivity<NoteViewState.Data?, NoteViewState>() {
    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, noteId: String? = null) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, noteId)
            context.startActivity(intent)
        }
    }

    override val layoutRes = R.layout.activity_note
    override val model: NoteViewModel by viewModel()

    private var note: Note? = null


    val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar1)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(EXTRA_NOTE)
        noteId?. let {
            model.loadNote(it)
        } ?: let {
            supportActionBar?.title = getString(R.string.title_new_note)
        }

    }


    override fun renderData(data: NoteViewState.Data?) {
        if (data?.isDeleted!!) {
            finish()
        }

        this.note = data?.note
        supportActionBar?.title = this.note?.let {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note!!.lastChanged)
        } ?: getString(R.string.title_new_note)

        initView()
    }

    fun initView() {
        note?.let { note ->
            et_title.setText(note.title)
            et_body.setText(note.text)
//            val color = note.color.getColorRes()
//            toolbar1.setBackgroundColor(ContextCompat.getColor(this, color))
//dva varianta
            toolbar1.setBackgroundColor(note.color.getColorInt(this))
        }

        et_title.addTextChangedListener(textChangeListener)
        et_body.addTextChangedListener(textChangeListener)
    }

    fun saveNote() {
        if (et_title.text == null || et_title.text!!.length < 3) return


        note = note?.copy(
                title = et_title.text.toString(),
                text = et_body.text.toString(),
                lastChanged = Date()
        ) ?: createNewNote()

        note?.let { model.save(it) }

    }

    private fun createNewNote(): Note =
        Note(UUID.randomUUID().toString(), et_title.text.toString(), et_body.text.toString())


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
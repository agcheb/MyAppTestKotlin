package com.gb.agcheb.myapptestkotlin.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.gb.agcheb.myapptestkotlin.R
import com.gb.agcheb.myapptestkotlin.commons.getColorInt
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<NoteData>() {
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
    private var color = Note.Color.WHITE


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
        noteId?.let {
            model.loadNote(it)
        } ?: let {
            supportActionBar?.title = getString(R.string.title_new_note)
            initView()
        }

    }


    override fun renderData(data: NoteData) {
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

            removeEditListener()
            if (et_title.text.toString() != note.title) {
                et_title.setText(note.title)
            }
            if (et_body.text.toString() != note.text) {
                et_body.setText(note.text)
            }
            color = note.color
//            val color = note.color.getColorRes()
//            toolbar1.setBackgroundColor(ContextCompat.getColor(this, color))
//dva varianta
            toolbar1.setBackgroundColor(note.color.getColorInt(this))
            supportActionBar?.title = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note.lastChanged)
        } ?: let {
            supportActionBar?.title =   getString(R.string.title_new_note)
        }

        setEditListener()

        colorPicker.onColorClickListener = {
            toolbar1.setBackgroundColor(color.getColorInt(this))
            color = it
            saveNote()
        }
    }

    private fun removeEditListener(){
        et_title.removeTextChangedListener(textChangeListener)
        et_body.removeTextChangedListener(textChangeListener)
    }

    private fun setEditListener(){
        et_title.addTextChangedListener(textChangeListener)
        et_body.addTextChangedListener(textChangeListener)
    }

    fun saveNote() {
        if (et_title.text == null || et_title.text!!.length < 3) return


        note = note?.copy(
                title = et_title.text.toString(),
                text = et_body.text.toString(),
                lastChanged = Date(),
                color = color
        ) ?: createNewNote()

        note?.let { model.save(it) }

    }

    private fun createNewNote(): Note =
            Note(UUID.randomUUID().toString(), et_title.text.toString(), et_body.text.toString())

    override fun onCreateOptionsMenu(menu: Menu?) = menuInflater.inflate(R.menu.note, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.palette -> togglePalette().let { true }
        R.id.delete -> deleteNote().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    private fun togglePalette() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }


    private fun deleteNote() {
        alert {
            messageResource = R.string.delete_sure_question
            positiveButton(R.string.logout_dialog_response_ok) { model.delete() }
            negativeButton(R.string.logout_dialog_response_no) { dialog -> dialog.dismiss() }
        }.show()
    }


}
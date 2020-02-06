package com.gb.agcheb.myapptestkotlin.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.gb.agcheb.myapptestkotlin.R
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_note.*
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {
    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
        private const val SAVE_DELAY = 2000L

        fun start(context: Context, note: Note? = null) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, note)
            context.startActivity(intent)
        }
    }

    private var note: Note? = null
    lateinit var viewModel: NoteViewModel


    val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_note)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        setSupportActionBar(toolbar)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        supportActionBar?.title = note?.let {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(it.lastChanged)
        } ?: getString(R.string.title_new_note)

        initView()
    }

    fun initView() {
        note?.let { note ->
            et_title.setText(note.title)
            et_body.setText(note.text)

            val color = when (note.color) {
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.GREEN -> R.color.green
                Note.Color.BLUE -> R.color.blue
                Note.Color.RED -> R.color.red
                Note.Color.VIOLET -> R.color.violet
                Note.Color.PINK -> R.color.pink
            }

            toolbar.setBackgroundColor(ContextCompat.getColor(this, color))
        }

        et_title.addTextChangedListener(textChangeListener)
        et_body.addTextChangedListener(textChangeListener)
    }

    fun saveNote() {
        if (et_title.text == null || et_title.text!!.length < 3) return

        Handler().postDelayed({
            note = note?.copy(
                    title = et_title.text.toString(),
                    text = et_body.text.toString(),
                    lastChanged = Date()
            ) ?: createNewNote()

            note?.let {viewModel.save(it)}
        }, SAVE_DELAY)
    }

    private fun createNewNote(): Note =
        Note(UUID.randomUUID().toString(), et_title.text.toString(), et_body.text.toString(), Note.Color.WHITE)


    override fun onOptionsItemSelected(item: MenuItem):Boolean = when(item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
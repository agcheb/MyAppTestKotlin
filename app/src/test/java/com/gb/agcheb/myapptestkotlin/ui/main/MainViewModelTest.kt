package com.gb.agcheb.myapptestkotlin.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.gb.agcheb.myapptestkotlin.data.NotesRepository
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.data.model.NoteResult
import com.gb.agcheb.myapptestkotlin.ui.main.MainViewModel
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val taskExevutorRule = InstantTaskExecutorRule()

    private val mockRepo = mockk<NotesRepository>()
    private val notesLiveData = MutableLiveData<NoteResult>()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        clearMocks(mockRepo)
        every { mockRepo.getNotes() } returns notesLiveData
        viewModel = MainViewModel(mockRepo)
    }

    @Test
    fun `should call getNotes`() {
        verify(exactly = 1) { mockRepo.getNotes() }
    }

    @Test
    fun `should return notes`() {
        var result: List<Note>? = null
        val testData = listOf(Note("1"), Note("2"))
        viewModel.getViewState().observeForever {
            result = it.data
        }
        notesLiveData.value = NoteResult.Success(testData)
        assertEquals(testData, result)
    }

    @Test
    fun `shoud return error`() {
        var  result: Throwable? = null
        val  testData = Throwable("error")
        viewModel.getViewState().observeForever {
            result = it?.error
        }
        notesLiveData.value = NoteResult.Error(testData)
        assertEquals(testData, result)
    }

    @Test
    fun  `should remove observer` () {

        viewModel.onCleared()
        assertFalse(notesLiveData.hasObservers())
    }
}
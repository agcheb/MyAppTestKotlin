package com.gb.agcheb.myapptestkotlin.data.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gb.agcheb.myapptestkotlin.data.entity.Note
import com.gb.agcheb.myapptestkotlin.data.exceptions.NoAuthException
import com.gb.agcheb.myapptestkotlin.data.model.NoteResult
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class FireStoreProviderTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDb = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()
    private val mockCollection = mockk<CollectionReference>()
    private val mockUser = mockk<FirebaseUser>()

    private val testNotes = listOf(Note("1"), Note("2"), Note("3"))

    private val mockDocument1 = mockk<DocumentSnapshot>()
    private val mockDocument2 = mockk<DocumentSnapshot>()
    private val mockDocument3 = mockk<DocumentSnapshot>()

    private val provider = FireStoreProvider(mockAuth, mockDb)

    companion object {
        @BeforeClass
        fun setupClass() {

        }

        @AfterClass
        fun shutDownClass() {

        }
    }

    @Before
    fun setup() {
        clearAllMocks()
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        every { mockDb.collection(any()).document(any()).collection(any()) } returns mockCollection
        every { mockDocument1.toObject(Note::class.java) } returns testNotes[0]
        every { mockDocument2.toObject(Note::class.java) } returns testNotes[1]
        every { mockDocument3.toObject(Note::class.java) } returns testNotes[2]


    }

    @After
    fun shutDown() {

    }


    @Test
    fun `should throw NoAuthException if no auth`() {
        var result: Any? = null
        every { mockAuth.currentUser } returns null
        provider.subscribeToAllNotes().observeForever {
            result = (it as? NoteResult.Error)?.error
        }
        assertTrue(result is NoAuthException)
    }

    @Test
    fun `savenote calls set`() {
        val mockDocumentReference = mockk<DocumentReference>()
        every { mockCollection.document(testNotes[0].id) } returns mockDocumentReference
        provider.saveNote(testNotes[0])
        verify(exactly = 1) { mockDocumentReference.set(testNotes[0]) }
    }

    @Test
    fun `subscribe to all notes return notes`() {
        var result: List<Note>? = null
        val mockSnapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockSnapshot.documents } returns listOf(mockDocument1, mockDocument2, mockDocument3)
        every { mockCollection.addSnapshotListener(capture(slot)) } returns mockk()

        provider.subscribeToAllNotes().observeForever {
            result = (it as? NoteResult.Success<List<Note>>)?.data
        }
        slot.captured.onEvent(mockSnapshot, null)
        assertEquals(testNotes, result)
    }

    //(error=io.mockk.MockKException: no answer found for: Task(#18).addOnFailureListener(com.gb.agcheb.myapptestkotlin.data.provider.FireStoreProvider$getNoteById$1$2@44cffc25))
    @Test
    fun `get note by id calls get`() {
        var result: Note? = null
        val slot = slot<OnSuccessListener<DocumentSnapshot>>()

        every { mockCollection.document(testNotes[0].id).get().addOnSuccessListener(capture(slot)) } returns mockk()

        provider.getNoteById(testNotes[0].id).observeForever {
            print("im not fuckup")
            print(it)
            result = (it as? NoteResult.Success<Note>)?.data
        }

        slot.captured.onSuccess(mockDocument1)

        assertEquals(testNotes[0], result)
    }
//    скопированный пример из классной работы последнего урока (не проходит тест)
    @Test
    fun `getNoteByIs should return Note`() {
        var result: Note? = null
        val slot = slot<OnSuccessListener<DocumentSnapshot>>()
        every { mockCollection.document(testNotes[0].id).get().addOnSuccessListener (capture(slot))} returns mockk()

        provider.getNoteById(testNotes[0].id).observeForever{
            result = (it as? NoteResult.Success<Note>)?.data
        }

        slot.captured.onSuccess(mockDocument1)
        assertEquals(testNotes[0], result)
    }

    @Test
    fun `delete note calls delete`() {
        val mockDocumentReference = mockk<DocumentReference>()
        every { mockCollection.document(testNotes[0].id) } returns mockDocumentReference
        provider.deleteNote(testNotes[0].id)
        verify(exactly = 1) { mockDocumentReference.delete() }
    }
}
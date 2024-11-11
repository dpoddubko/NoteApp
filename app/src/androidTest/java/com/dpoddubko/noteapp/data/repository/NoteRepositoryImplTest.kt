package com.dpoddubko.noteapp.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dpoddubko.noteapp.data.local.NoteDao
import com.dpoddubko.noteapp.data.local.NoteDatabase
import com.dpoddubko.noteapp.domain.model.Note
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NoteRepositoryImplTest {

    private lateinit var db: NoteDatabase
    private lateinit var dao: NoteDao
    private lateinit var repository: NoteRepositoryImpl

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.noteDao()
        repository = NoteRepositoryImpl(dao)
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insert_and_getAllNotes_returns_inserted_notes() = runTest {
        // Arrange
        val note = Note(title = "Test Note", content = "Test Content")

        // Act
        repository.insertNote(note)

        // Assert
        val notes = repository.getAllNotes().first()
        assertEquals(1, notes.size)
        assertEquals(note.title, notes[0].title)
        assertEquals(note.content, notes[0].content)
    }

    @Test
    fun delete_note_removes_the_note() = runTest {
        // Arrange

        val note = Note(id = 1, title = "Test Note", content = "Test Content")
        repository.insertNote(note)

        // Act
        repository.deleteNote(note)

        // Assert
        val notes = repository.getAllNotes().first()
        assertEquals(0, notes.size)
    }
}
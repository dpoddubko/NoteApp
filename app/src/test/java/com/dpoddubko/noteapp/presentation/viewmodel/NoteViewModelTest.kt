package com.dpoddubko.noteapp.presentation.viewmodel

import app.cash.turbine.test
import com.dpoddubko.noteapp.domain.model.Note
import com.dpoddubko.noteapp.domain.usecase.AddNoteUseCase
import com.dpoddubko.noteapp.domain.usecase.DeleteNoteUseCase
import com.dpoddubko.noteapp.domain.usecase.GetAllNotesUseCase
import com.dpoddubko.noteapp.presentation.ui.main.NoteViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NoteViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getAllNotesUseCase: GetAllNotesUseCase
    private lateinit var addNoteUseCase: AddNoteUseCase
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var viewModel: NoteViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getAllNotesUseCase = mockk()
        addNoteUseCase = mockk(relaxed = true)
        deleteNoteUseCase = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Helper method to initialize the ViewModel with mock data
    private suspend fun initViewModelWithMockData(vararg notesSequences: List<Note>) {
        coEvery { getAllNotesUseCase.invoke() } returns flow {
            notesSequences.forEach { notes ->
                emit(notes)
            }
        }
        viewModel = NoteViewModel(getAllNotesUseCase, addNoteUseCase, deleteNoteUseCase)
    }

    // Helper method to assert UI state and run pending tasks
    private suspend fun assertUiState(expectedNotes: List<Note>) {
        testDispatcher.scheduler.advanceUntilIdle()  // Run pending coroutines
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(expectedNotes, state.notes)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `initial UI state contains notes from use case`() = runTest {
        // Arrange
        val mockNotes = listOf(
            Note(id = 1, title = "Note 1", content = "Content 1"),
            Note(id = 2, title = "Note 2", content = "Content 2")
        )

        initViewModelWithMockData(mockNotes)  // Initialize ViewModel with initial data

        // Act & Assert
        assertUiState(expectedNotes = mockNotes)
    }

    @Test
    fun `addNote updates the notes list`() = runTest {
        // Arrange
        val initialNotes = listOf(
            Note(id = 1, title = "Note 1", content = "Content 1")
        )
        val newNote = Note(id = 2, title = "Note 2", content = "Content 2")

        // Set up mock sequence: initial notes, then notes with the new note added
        initViewModelWithMockData(initialNotes, initialNotes + newNote)

        // Act
        viewModel.addNote(newNote.title, newNote.content)

        // Assert
        assertUiState(expectedNotes = initialNotes + newNote)
    }

    @Test
    fun `deleteNote removes the note from the list`() = runTest {
        // Arrange
        val initialNotes = listOf(
            Note(id = 1, title = "Note 1", content = "Content 1"),
            Note(id = 2, title = "Note 2", content = "Content 2")
        )
        val updatedNotes = listOf(
            Note(id = 1, title = "Note 1", content = "Content 1")
        )

        // Set up mock sequence: initial notes, then notes with one note removed
        initViewModelWithMockData(initialNotes, updatedNotes)

        // Act
        viewModel.deleteNote(initialNotes[1])

        // Assert
        assertUiState(expectedNotes = updatedNotes)
    }
}

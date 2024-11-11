package com.dpoddubko.noteapp.domain.usecase


import app.cash.turbine.test
import com.dpoddubko.noteapp.domain.model.Note
import com.dpoddubko.noteapp.domain.repository.NoteRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAllNotesUseCaseTest {

    private lateinit var repository: NoteRepository
    private lateinit var useCase: GetAllNotesUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetAllNotesUseCase(repository)
    }

    @Test
    fun `invoke returns list of notes`() = runTest {
        // Arrange
        val mockNotes = listOf(
            Note(id = 1, title = "Note 1", content = "Content 1"),
            Note(id = 2, title = "Note 2", content = "Content 2")
        )
        coEvery { repository.getAllNotes() } returns flowOf(mockNotes)

        // Act & Assert
        useCase().test {
            val result = awaitItem()
            assertEquals(mockNotes, result)
            cancelAndConsumeRemainingEvents()
        }
    }
}
package com.dpoddubko.noteapp.domain.repository

import com.dpoddubko.noteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun clearNotes()
    suspend fun updateNote(note: Note)
    suspend fun getNoteById(id: Int): Note?
}
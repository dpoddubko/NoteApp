package com.dpoddubko.noteapp.data.repository

import com.dpoddubko.noteapp.data.local.NoteDao
import com.dpoddubko.noteapp.data.mapper.toDomain
import com.dpoddubko.noteapp.data.mapper.toEntity
import com.dpoddubko.noteapp.domain.model.Note
import com.dpoddubko.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toEntity())
    }

    override suspend fun clearNotes() {
        dao.clearNotes()
    }

    override suspend fun updateNote(note: Note) = dao.updateNote(note.toEntity())

    override suspend fun getNoteById(id: Int): Note? = dao.getNoteById(id)
}
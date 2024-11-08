package com.dpoddubko.noteapp.domain.usecase

import com.dpoddubko.noteapp.domain.model.Note
import com.dpoddubko.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> = repository.getAllNotes()
}
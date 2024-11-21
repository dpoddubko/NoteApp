package com.dpoddubko.noteapp.domain.usecase

import com.dpoddubko.noteapp.domain.model.Note
import com.dpoddubko.noteapp.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}
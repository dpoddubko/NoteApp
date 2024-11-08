package com.dpoddubko.noteapp.domain.usecase

import com.dpoddubko.noteapp.domain.model.Note
import com.dpoddubko.noteapp.domain.repository.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) = repository.insertNote(note)
}
package com.dpoddubko.noteapp.domain.usecase

import com.dpoddubko.noteapp.domain.repository.NoteRepository
import javax.inject.Inject

class ClearNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke() = repository.clearNotes()
}
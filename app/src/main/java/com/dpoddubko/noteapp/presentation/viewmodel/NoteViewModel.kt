package com.dpoddubko.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpoddubko.noteapp.domain.model.Note
import com.dpoddubko.noteapp.domain.usecase.AddNoteUseCase
import com.dpoddubko.noteapp.domain.usecase.DeleteNoteUseCase
import com.dpoddubko.noteapp.domain.usecase.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        getAllNotesUseCase().onEach { notes ->
            _uiState.update { it.copy(notes = notes) }
        }.launchIn(viewModelScope)
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            addNoteUseCase(Note(title = title, content = content))
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
        }
    }
}

data class NoteUiState(
    val notes: List<Note> = emptyList()
)
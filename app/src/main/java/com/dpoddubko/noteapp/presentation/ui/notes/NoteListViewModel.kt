package com.dpoddubko.noteapp.presentation.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpoddubko.noteapp.domain.model.Note
import com.dpoddubko.noteapp.domain.usecase.AddNoteUseCase
import com.dpoddubko.noteapp.domain.usecase.ClearNotesUseCase
import com.dpoddubko.noteapp.domain.usecase.DeleteNoteUseCase
import com.dpoddubko.noteapp.domain.usecase.GetAllNotesUseCase
import com.dpoddubko.noteapp.domain.usecase.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val clearNotesUseCase: ClearNotesUseCase
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
            addNoteUseCase.invoke(Note(title = title, content = content))
        }
    }

    fun updateNote(id: Int, title: String, content: String) {
        viewModelScope.launch {
            updateNoteUseCase.invoke(Note(id = id, title = title, content = content))
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase.invoke(note)
        }
    }

    fun clearNotes() {
        viewModelScope.launch {
            clearNotesUseCase.invoke()
        }
    }
}

data class NoteUiState(
    val notes: List<Note> = emptyList()
)
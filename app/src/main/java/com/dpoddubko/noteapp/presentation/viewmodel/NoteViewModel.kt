package com.dpoddubko.noteapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpoddubko.noteapp.domain.model.Note
import com.dpoddubko.noteapp.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val clearNotesUseCase: ClearNotesUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase // Добавлено
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    // Дополнительный State для редактируемой заметки
    private val _editNoteState = MutableStateFlow<Note?>(null)
    val editNoteState: StateFlow<Note?> = _editNoteState.asStateFlow()

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        getAllNotesUseCase().onEach { notes ->
            _uiState.update { it.copy(notes = notes) }
        }.launchIn(viewModelScope)
    }

    fun addNote(title: String, content: String, color: Int) {
        viewModelScope.launch {
            addNoteUseCase.invoke(Note(title = title, content = content, color = color))
        }
    }

    fun updateNote(id: Int, title: String, content: String, color: Int) {
        viewModelScope.launch {
            updateNoteUseCase.invoke(Note(id = id, title = title, content = content, color = color))
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

    // Добавлено: Получение заметки по ID для редактирования
    fun getNoteById(id: Int) {
        viewModelScope.launch {
            val note = getNoteByIdUseCase.invoke(id)
            _editNoteState.value = note
        }
    }

    // Очистка выбранной заметки после редактирования
    fun clearEditNote() {
        _editNoteState.value = null
    }
}

data class NoteUiState(
    val notes: List<Note> = emptyList()
)
// EditNoteViewModel.kt
package com.dpoddubko.noteapp.presentation.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpoddubko.noteapp.domain.model.Note
import com.dpoddubko.noteapp.domain.usecase.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase
) : ViewModel() {

    // Определите UI state и методы для редактирования/сохранения заметок

    fun saveNote(id: Int?, title: String, content: String) {
        viewModelScope.launch {
            if (id == null) {
                // Реализуйте AddNoteUseCase, если необходимо
            } else {
                updateNoteUseCase.invoke(Note(id = id, title = title, content = content))
            }
        }
    }
}
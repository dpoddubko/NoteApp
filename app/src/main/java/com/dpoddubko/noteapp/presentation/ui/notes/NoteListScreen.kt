package com.dpoddubko.noteapp.presentation.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dpoddubko.noteapp.domain.model.Note
import com.dpoddubko.noteapp.presentation.viewmodel.NoteViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel(),
    onAddClick: () -> Unit,
    onEditClick: (Int) -> Unit,
    onClearClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Список Заметок") },
                actions = {
                    IconButton(onClick = { onClearClick() }) {
                        Icon(Icons.Default.Delete, contentDescription = "Очистить Заметки")
                    }
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Настройки")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Добавить Заметку")
            }
        }
    ) { paddingValues ->
        if (uiState.notes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Нет доступных заметок.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(uiState.notes) { note ->
                    NoteItem(
                        note = note,
                        onEditClick = { onEditClick(note.id) },
                        onDeleteClick = { viewModel.deleteNote(note) }
                    )
                    Divider()
                }
            }
        }
    }
}
@Composable
fun NoteItem(
    note: Note,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEditClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
// Цветовая полоса
        Box(
            modifier = Modifier
                .width(8.dp)
                .height(60.dp)
                .background(Color(note.color))
        )
        Spacer(modifier = Modifier.width(8.dp))
// Контейнер для заголовка и содержания
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
// Кнопка "Удалить"
        IconButton(onClick = { onDeleteClick() }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Удалить Заметку",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}
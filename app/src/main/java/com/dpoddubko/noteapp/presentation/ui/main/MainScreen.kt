package com.dpoddubko.noteapp.presentation.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dpoddubko.noteapp.domain.model.Note
import com.dpoddubko.noteapp.presentation.ui.main.MainScreenConstants.NO_NOTES_AVAILABLE_TEXT
import com.dpoddubko.noteapp.presentation.ui.main.MainScreenConstants.YOUR_NOTES_TEXT

object MainScreenConstants {
    const val YOUR_NOTES_TEXT = "Your Notes"
    const val NO_NOTES_AVAILABLE_TEXT = "No notes available."
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: NoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var title by rememberSaveable  { mutableStateOf("") }
    var content by rememberSaveable  { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Note App") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (title.isNotBlank() && content.isNotBlank()) {
                    viewModel.addNote(title, content)
                    title = ""
                    content = ""
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(YOUR_NOTES_TEXT, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            if (uiState.notes.isEmpty()) {
                Text(NO_NOTES_AVAILABLE_TEXT, style = MaterialTheme.typography.bodyMedium)
            } else {
                NoteList(notes = uiState.notes, onDelete = { note ->
                    viewModel.deleteNote(note)
                })
            }
        }
    }
}

@Composable
fun NoteList(notes: List<Note>, onDelete: (Note) -> Unit) {
    LazyColumn {
        items(notes) { note ->
            NoteItem(note = note, onDelete = onDelete)
        }
    }
}

@Composable
fun NoteItem(note: Note, onDelete: (Note) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onDelete(note) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Note")
            }
        }
    }
}


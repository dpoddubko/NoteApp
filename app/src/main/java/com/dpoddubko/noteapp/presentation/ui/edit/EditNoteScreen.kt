package com.dpoddubko.noteapp.presentation.ui.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dpoddubko.noteapp.R
import com.dpoddubko.noteapp.presentation.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel(),
    noteId: Int?,
    onSave: () -> Unit
) {
    val editNote by viewModel.editNoteState.collectAsState()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(Color.White) } // Цвет заметки
// Загружаем заметку при первом запуске Composable
    LaunchedEffect(noteId) {
        if (noteId != null) {
            viewModel.getNoteById(noteId)
        }
    }
// Обновляем поля при загрузке заметки
    LaunchedEffect(editNote) {
        editNote?.let {
            title = it.title
            content = it.content
            color = Color(it.color)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null) "Добавить Заметку" else "Редактировать Заметку") },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.clearEditNote() // Очистка состояния
                        navController.popBackStack()
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (noteId == null) {
                    viewModel.addNote(title, content, color.toArgb())
                } else {
                    viewModel.updateNote(noteId, title, content, color.toArgb())
                }
                onSave()
                viewModel.clearEditNote() // Очистка состояния после сохранения
            }) {
                Icon(
                    painterResource(id = R.drawable.ic_save),
                    contentDescription = "Сохранить Заметку"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
// Фоновое изображение
//            Image(
//                painter = painterResource(id = R.drawable.notebook_background),
//                contentDescription = "Фон тетради",
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
// Контент с полосами прокрутки
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Заголовок") },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Содержание") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    maxLines = 10,
                    singleLine = false
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Выберите цвет заметки:")
                Spacer(modifier = Modifier.height(8.dp))
                ColorPicker(selectedColor = color, onColorSelected = { selectedColor ->
                    color = selectedColor
                })
            }
        }
    }
}

@Composable
fun ColorPicker(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    val colors = listOf(
        Color.White,
        Color(0xFFFFCDD2),
        Color(0xFFC8E6C9),
        Color(0xFFBBDEFB),
        Color(0xFFFFF9C4),
        Color(0xFFE1BEE7),
        Color(0xFFFFECB3),
        Color(0xFFB0BEC5)
    )
    LazyRow {
        items(colors.size) { index ->
            val color = colors[index]
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .background(color)
                    .border(
                        width = if (color == selectedColor) 2.dp else 1.dp,
                        color = if (color == selectedColor) Color.Black else Color.Gray,
                        shape = MaterialTheme.shapes.small
                    )
                    .clickable { onColorSelected(color) }
            )
        }
    }
}
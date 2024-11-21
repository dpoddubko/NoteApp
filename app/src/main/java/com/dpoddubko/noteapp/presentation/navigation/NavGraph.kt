package com.dpoddubko.noteapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dpoddubko.noteapp.presentation.ui.edit.EditNoteScreen
import com.dpoddubko.noteapp.presentation.ui.notes.NoteListScreen
import com.dpoddubko.noteapp.presentation.ui.settings.SettingsScreen
import com.dpoddubko.noteapp.presentation.viewmodel.NoteViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "notes") {
        composable("notes") {
            val noteViewModel: NoteViewModel = hiltViewModel()
            NoteListScreen(
                navController = navController,
                viewModel = noteViewModel,
                onAddClick = { navController.navigate("edit") },
                onEditClick = { noteId ->
                    navController.navigate("edit/$noteId")
                },
                onClearClick = { noteViewModel.clearNotes() }
            )
        }
        composable(
            route = "edit/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId")
            EditNoteScreen(
                navController = navController,
                viewModel = hiltViewModel(),
                noteId = noteId,
                onSave = { navController.popBackStack() }
            )
        }
        composable("edit") {
            EditNoteScreen(
                navController = navController,
                viewModel = hiltViewModel(),
                noteId = null,
                onSave = { navController.popBackStack() }
            )
        }
        composable("settings") {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
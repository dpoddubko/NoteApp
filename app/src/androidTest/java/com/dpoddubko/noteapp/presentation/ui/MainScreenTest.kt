package com.dpoddubko.noteapp.presentation.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dpoddubko.noteapp.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun addNote_displaysInList() {
        // Presence of text fields
        composeTestRule.onNodeWithText("Title").assertExists()
        composeTestRule.onNodeWithText("Content").assertExists()

        // Entering text into fields
        composeTestRule.onNodeWithText("Title").performTextInput("New Note")
        composeTestRule.onNodeWithText("Content").performTextInput("This is a new note.")

        // Press FAB to add a note
        composeTestRule.onNodeWithContentDescription("Add Note").performClick()

        // Checking that the new note is displayed in the list
        composeTestRule.onNodeWithText("New Note").assertIsDisplayed()
        composeTestRule.onNodeWithText("This is a new note.").assertIsDisplayed()
    }

    @Test
    fun deleteNote_removesFromList() {
        // It is assumed that there is at least one note in the list
        // To make things simple, let's add a note first

        composeTestRule.onNodeWithText("Title").performTextInput("Note to Delete")
        composeTestRule.onNodeWithText("Content").performTextInput("This note will be deleted.")
        composeTestRule.onNodeWithContentDescription("Add Note").performClick()

        // Let's make sure the note appears.
        composeTestRule.onNodeWithText("Note to Delete").assertIsDisplayed()
        composeTestRule.onNodeWithText("This note will be deleted.").assertIsDisplayed()

        // Click on the delete icon
        composeTestRule.onNodeWithContentDescription("Delete Note").performClick()

        // Checking that the note has been deleted
        composeTestRule.onNodeWithText("Note to Delete").assertDoesNotExist()
        composeTestRule.onNodeWithText("This note will be deleted.").assertDoesNotExist()
    }
}
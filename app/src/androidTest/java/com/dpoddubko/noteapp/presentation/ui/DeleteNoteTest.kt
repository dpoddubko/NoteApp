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
class DeleteNoteTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun deleteNote_removesFromList() {
        // Add a note to delete
        composeTestRule.onNodeWithText("Title").performTextInput("Note to Delete")
        composeTestRule.onNodeWithText("Content").performTextInput("This note will be deleted.")
        composeTestRule.onNodeWithContentDescription("Add Note").performClick()

        // Check the note exist
        composeTestRule.onNodeWithText("Note to Delete").assertIsDisplayed()
        composeTestRule.onNodeWithText("This note will be deleted.").assertIsDisplayed()

        // Click a button to delete the note
        composeTestRule.onNode(hasContentDescription("Delete Note")).performClick()

        // Check the note was removd
        composeTestRule.onNodeWithText("Note to Delete").assertDoesNotExist()
        composeTestRule.onNodeWithText("This note will be deleted.").assertDoesNotExist()
    }
}
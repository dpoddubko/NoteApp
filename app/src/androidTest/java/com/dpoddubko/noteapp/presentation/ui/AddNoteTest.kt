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
class AddNoteTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun addNote_updatesNoteList() {
        // input a title and a content
        composeTestRule.onNodeWithText("Title").performTextInput("UI Test Note")
        composeTestRule.onNodeWithText("Content").performTextInput("This is a UI test.")

        // click on add button
        composeTestRule.onNodeWithContentDescription("Add Note").performClick()

        // Check that the new note is displayed
        composeTestRule.onNodeWithText("UI Test Note").assertIsDisplayed()
        composeTestRule.onNodeWithText("This is a UI test.").assertIsDisplayed()
    }
}
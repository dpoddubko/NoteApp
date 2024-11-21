package com.dpoddubko.noteapp.presentation.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dpoddubko.noteapp.presentation.ui.main.MainScreenConstants.NO_NOTES_AVAILABLE_TEXT
import com.dpoddubko.noteapp.presentation.ui.main.MainScreenConstants.YOUR_NOTES_TEXT
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class EmptyListTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun whenNoNotes_displayEmptyState() {
        // Expecting the list to be empty
        composeTestRule.onNodeWithText(YOUR_NOTES_TEXT).assertIsDisplayed()
        composeTestRule.onNodeWithText(NO_NOTES_AVAILABLE_TEXT).assertIsDisplayed()
    }
}
package com.dpoddubko.noteapp.domain.model

data class Note(
    val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)
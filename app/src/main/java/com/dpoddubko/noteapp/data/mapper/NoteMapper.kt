package com.dpoddubko.noteapp.data.mapper

import com.dpoddubko.noteapp.data.local.NoteEntity
import com.dpoddubko.noteapp.domain.model.Note

fun NoteEntity.toDomain() =
    Note(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color
    )

fun Note.toEntity() =
    NoteEntity(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp,
        color = color
    )
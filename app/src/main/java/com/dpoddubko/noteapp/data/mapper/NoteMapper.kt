package com.dpoddubko.noteapp.data.mapper

import com.dpoddubko.noteapp.data.local.NoteEntity
import com.dpoddubko.noteapp.domain.model.Note

fun NoteEntity.toDomain(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        timestamp = timestamp
    )
}
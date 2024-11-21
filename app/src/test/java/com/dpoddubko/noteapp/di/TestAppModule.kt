package com.dpoddubko.noteapp.di

import com.dpoddubko.noteapp.domain.repository.NoteRepository
import com.dpoddubko.noteapp.domain.usecase.AddNoteUseCase
import com.dpoddubko.noteapp.domain.usecase.DeleteNoteUseCase
import com.dpoddubko.noteapp.domain.usecase.GetAllNotesUseCase
import com.dpoddubko.noteapp.domain.usecase.GetNoteByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mockk.mockk
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteRepository(): NoteRepository = mockk(relaxed = true)

    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(repository: NoteRepository): GetAllNotesUseCase {
        return GetAllNotesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddNoteUseCase(repository: NoteRepository): AddNoteUseCase {
        return AddNoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(repository: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetNoteByIdUseCase(repository: NoteRepository): GetNoteByIdUseCase {
        return GetNoteByIdUseCase(repository)
    }
}
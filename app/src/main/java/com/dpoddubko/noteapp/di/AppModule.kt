package com.dpoddubko.noteapp.di

import android.content.Context
import androidx.room.Room
import com.dpoddubko.noteapp.data.local.NoteDao
import com.dpoddubko.noteapp.data.local.NoteDatabase
import com.dpoddubko.noteapp.data.repository.NoteRepositoryImpl
import com.dpoddubko.noteapp.domain.repository.NoteRepository
import com.dpoddubko.noteapp.domain.usecase.AddNoteUseCase
import com.dpoddubko.noteapp.domain.usecase.ClearNotesUseCase
import com.dpoddubko.noteapp.domain.usecase.DeleteNoteUseCase
import com.dpoddubko.noteapp.domain.usecase.GetAllNotesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        repositoryImpl: NoteRepositoryImpl
    ): NoteRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }

    @Provides
    fun provideNoteDao(db: NoteDatabase): NoteDao {
        return db.noteDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

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
    fun provideClearNotesUseCase(repository: NoteRepository): ClearNotesUseCase {
        return ClearNotesUseCase(repository)
    }
}
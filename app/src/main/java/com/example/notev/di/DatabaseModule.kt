package com.example.notev.di

import android.content.Context
import androidx.room.Room
import com.example.notev.data.Repository
import com.example.notev.data.room.NoteDao
import com.example.notev.data.room.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Creates [NoteDatabase].
     */
    @Provides
    @Singleton
    fun provideNoteDao(
        @ApplicationContext context: Context
    ): NoteDao {
        val db = Room
            .databaseBuilder(
                context, // application context
                NoteDatabase::class.java, // database class
                "note_database" // database name
            )
            .build() // builds room database
        return db.noteDao() // returns its Dao interface
    }
}
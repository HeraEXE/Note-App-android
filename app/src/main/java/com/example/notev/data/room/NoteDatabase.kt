package com.example.notev.data.room

import androidx.room.Database
import com.example.notev.data.models.Note

/**
 * Note Database
 */
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase {

    /**
     * Creates database and returns its interface.
     * @return [NoteDao] interface.
     */
    abstract fun noteDao(): NoteDao
}
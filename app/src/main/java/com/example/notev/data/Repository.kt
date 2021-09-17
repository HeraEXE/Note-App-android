package com.example.notev.data

import com.example.notev.data.models.Note
import com.example.notev.data.room.NoteDao
import com.example.notev.data.room.NoteDatabase
import com.example.notev.utils.Sorting
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository.
 */
class Repository @Inject constructor(
    private val noteDao: NoteDao
) {

    /**
     * Inserts new note in the [NoteDatabase].
     * @param uses [Note] object to add.
     */
    suspend fun insert(note: Note) = noteDao.insert(note)


    /**
     * Updated edited note in the [NoteDatabase].
     * @param uses [Note] object to update.
     */
    suspend fun update(note: Note) = noteDao.update(note)


    /**
     * Deletes note from the [NoteDatabase].
     * @param uses [Note] object to delete.
     */
    suspend fun delete(note: Note) = noteDao.delete(note)


    /**
     * Gets notes from the [NoteDatabase].
     * @param uses [query] to search for note by their titles and [sortBy] to sort notes by [Sorting] variants.
     * @return [Flow] - [List] of [Note]s.
     */
    fun getAllNotes(query: String, sortBy: Sorting) = noteDao.getNotes(query, sortBy)
}
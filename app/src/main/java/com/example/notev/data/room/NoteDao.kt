package com.example.notev.data.room

import androidx.room.*
import com.example.notev.data.models.Note
import com.example.notev.utils.Sorting
import kotlinx.coroutines.flow.Flow

/**
 * Note Dao
 */
@Dao
interface NoteDao {

    /**
     * Inserts new note in the [NoteDatabase].
     * @param uses [Note] object to add.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)


    /**
     * Updated edited note in the [NoteDatabase].
     * @param uses [Note] object to update.
     */
    @Update
    suspend fun update(note: Note)


    /**
     * Deletes note from the [NoteDatabase].
     * @param uses [Note] object to delete.
     */
    @Delete
    suspend fun delete(note: Note)


    /**
     * Order by Newest (or by date DESC).
     * @param uses [query] string for getting results by note's title.
     * @return [Flow] - [List] of [Note]s.
     */
    @Query("SELECT * FROM note_table WHERE title LIKE '%' || :query || '%' ORDER BY date DESC")
    fun orderedByNewest(query: String): Flow<List<Note>>


    /**
     * Order by Oldest (or by date ASC).
     * @param uses [query] string for getting results by note's title.
     * @return [Flow] - [List] of [Note]s.
     */
    @Query("SELECT * FROM note_table WHERE title LIKE '%' || :query || '%' ORDER BY date")
    fun orderedByOldest(query: String): Flow<List<Note>>


    /**
     * Order by Low Level (or by priority_level ASC).
     * @param uses [query] string for getting results by note's title.
     * @return [Flow] - [List] of [Note]s.
     */
    @Query("SELECT * FROM note_table WHERE title LIKE '%' || :query || '%' ORDER BY priority_level")
    fun orderedByLowLevel(query: String): Flow<List<Note>>


    /**
     * Order by High Level (or by priority_level DESC).
     * @param uses [query] string for getting results by note's title.
     * @return [Flow] - [List] of [Note]s.
     */
    @Query("SELECT * FROM note_table WHERE title LIKE '%' || :query || '%' ORDER BY priority_level DESC")
    fun orderedByHighLevel(query: String): Flow<List<Note>>


    /**
     * Gets notes from the [NoteDatabase].
     * @param uses [query] to search for note by their titles and [sortBy] to sort notes by [Sorting] variants.
     * @return [Flow] - [List] of [Note]s.
     */
    fun getNotes(query: String, sortBy: Sorting) = when (sortBy) {
        Sorting.BY_NEWEST -> orderedByNewest(query) // newest
        Sorting.BY_OLDEST -> orderedByOldest(query) // oldest
        Sorting.BY_LOW_LEVEL -> orderedByLowLevel(query) // low level
        Sorting.BY_HIGH_LEVEL -> orderedByHighLevel(query) // high level
    }
}
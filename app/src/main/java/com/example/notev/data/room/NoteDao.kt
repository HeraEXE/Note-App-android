package com.example.notev.data.room

import androidx.room.*
import com.example.notev.data.model.Note

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
    suspend fun insert(note: Note) // inserts new note


    /**
     * Updated edited note in the [NoteDatabase].
     * @param uses [Note] object to update.
     */
    @Update
    suspend fun update(note: Note) // update edited note


    /**
     * Deletes note from the [NoteDatabase].
     * @param uses [Note] object to delete.
     */
    @Delete
    suspend fun delete(note: Note) // deletes note


//    @Query("SELECT * FROM note_table WHERE  priority_level = 0 OR priority_level = 1 OR priority_level = 2 ELSE priority_level = :show END AND title LIKE '%' || :query || '%' ORDER BY date")
//    fun orderedByDate(query: String): Flow<List<Note>>
//
//
//    @Query("SELECT * FROM note_table WHERE CASE WHEN :show = 3 THEN priority_level = 0 OR priority_level = 1 OR priority_level = 2 ELSE priority_level = :show END AND title LIKE '%' || :query || '%' ORDER BY date DESC")
//    fun orderedByDateDesc(query: String): Flow<List<Note>>
//
//
//    @Query("SELECT * FROM note_table WHERE CASE WHEN :show = 3 THEN priority_level = 0 OR priority_level = 1 OR priority_level = 2 ELSE priority_level = :show END AND title LIKE '%' || :query || '%' ORDER BY priority_level")
//    fun orderedByPriority(query: String): Flow<List<Note>>
//
//
//    @Query("SELECT * FROM note_table WHERE CASE WHEN :show = 3 THEN priority_level = 0 OR priority_level = 1 OR priority_level = 2 ELSE priority_level = :show END AND title LIKE '%' || :query || '%' ORDER BY priority_level DESC")
//    fun orderedByPriorityDesc(query: String): Flow<List<Note>>


//    fun getAllNotes(query: String, sortBy: Int) = when (sortBy) {
//        SORT_BY_DATE_DESC -> orderedByDateDesc(query)
//        SORT_BY_DATE -> orderedByDate(query)
//        SORT_BY_PRIORITY -> orderedByPriority(query)
//        SORT_BY_PRIORITY_DESC -> orderedByPriorityDesc(query)
//        else -> orderedByDateDesc(query)
//    }

    // TODO(implement sorting, querying and getting list of notes from the database)
}
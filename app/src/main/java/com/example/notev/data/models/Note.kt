package com.example.notev.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Note data class.
 */
@Parcelize
@Entity(tableName = "note_table")
data class Note(

    var title: String, // note title

    var content: String, // note content

    @ColumnInfo(name = "priority_level")
    var priorityLevel: Int, // note level

    val date: Long = System.currentTimeMillis(), // date of creation

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null // primary key (auto-generates)
): Parcelable
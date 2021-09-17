package com.example.notev.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notev.data.Repository
import com.example.notev.data.models.Note
import com.example.notev.data.room.NoteDatabase
import com.example.notev.ui.AddEditNoteActivity
import com.example.notev.utils.NoteStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for [AddEditNoteActivity].
 */
@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    // NoteStatus to determine whether create new or edit existing note object.
    var noteStatus = NoteStatus.ADD


    /**
     * Inserts new note in the [NoteDatabase].
     * @param uses [Note] object to add.
     */
    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note) // calls repository suspend function
    }


    /**
     * Updated edited note in the [NoteDatabase].
     * @param uses [Note] object to update.
     */
    fun update(note: Note) = viewModelScope.launch {
        repository.update(note) // calls repository suspend function
    }


    /**
     * Deletes note from the [NoteDatabase].
     * @param uses [Note] object to delete.
     */
    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note) // calls repository suspend function
    }
}
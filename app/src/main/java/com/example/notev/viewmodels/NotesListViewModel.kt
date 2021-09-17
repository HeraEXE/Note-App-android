package com.example.notev.viewmodels

import androidx.lifecycle.ViewModel
import com.example.notev.data.Repository
import com.example.notev.ui.AddEditNoteActivity
import com.example.notev.ui.NotesListActivity
import com.example.notev.utils.Sorting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

/**
 * ViewModel for [NotesListActivity].
 */
@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    // Search query flow.
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    // Sorting flow.
    private val _sort = MutableStateFlow(Sorting.BY_OLDEST)
    val sort: StateFlow<Sorting> get() = _sort

    // Notes gets flow list of notes (Flow<List<Note>>) when query and/or sorting change their values.
    val notes = combine(query, sort) { query, sort ->
        query to sort // returns Pair(query, sort) to flatMapLatest
    }.flatMapLatest { (query, sort) ->
        repository.getAllNotes(query, sort) // returns Flow<List<Note>> to notes variable
    }


    /**
     * Sets query value to flow.
     */
    fun setQuery(str: String) {
        _query.value = str
    }


    /**
     * Sets sorting value to flow.
     */
    fun setSorting(sorting: Sorting) {
        _sort.value = sorting
    }
}
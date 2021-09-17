package com.example.notev.viewmodels

import androidx.lifecycle.ViewModel
import com.example.notev.data.Repository
import com.example.notev.utils.Sorting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    // Search query flow.
    val query = MutableStateFlow("")

    // Sorting flow.
    val sort = MutableStateFlow(Sorting.BY_NEWEST)

    // Notes gets flow list of notes (Flow<List<Note>>) when query and/or sorting change their values.
    val notes = combine(query, sort) { query, sort ->
        query to sort // returns Pair(query, sort) to flatMapLatest
    }.flatMapLatest { (query, sort) ->
        repository.getAllNotes(query, sort) // returns Flow<List<Note>> to notes variable
    }

}
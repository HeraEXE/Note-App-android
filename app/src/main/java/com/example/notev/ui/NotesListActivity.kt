package com.example.notev.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.notev.R
import com.example.notev.viewmodels.NotesListViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * The start point (launcher) [Activity].
 * Shows user's list of notes.
 */
@AndroidEntryPoint
class NotesListActivity : AppCompatActivity() {

    // View Model.
    private val viewModel: NotesListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set layout.
        setContentView(R.layout.activity_notes_list)
    }
}
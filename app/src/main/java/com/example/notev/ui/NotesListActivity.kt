package com.example.notev.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notev.R
import com.example.notev.adapter.NotesListAdapter
import com.example.notev.data.models.Note
import com.example.notev.databinding.ActivityNotesListBinding
import com.example.notev.utils.extension.startSpecificActivity
import com.example.notev.viewmodels.NotesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * The start point (launcher) [Activity].
 * Shows user's list of notes.
 */
@AndroidEntryPoint
class NotesListActivity : AppCompatActivity(), NotesListAdapter.Listener {

    // View Model.
    private val viewModel: NotesListViewModel by viewModels()

    // Adapter.
    private lateinit var adapter: NotesListAdapter

    // Binding.
    private lateinit var binding: ActivityNotesListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set binding.
        binding = ActivityNotesListBinding.inflate(layoutInflater)

        // Set layout.
        setContentView(binding.root)

        // Set title to action bar.
        supportActionBar?.title = getString(R.string.notes_list_activity_titles)

        // Set adapter.
        adapter = NotesListAdapter(context = this, listener = this)

        // Init Recycler View.
        binding.rvNotesList.also {
            it.adapter = adapter // set adapter to recycler view
            it.layoutManager =
                StaggeredGridLayoutManager(
                    2,
                    StaggeredGridLayoutManager.VERTICAL
                ) // set layout manager to recycler view
        }

        // Init Fab
        binding.fabAddNote.setOnClickListener {
            startSpecificActivity(AddEditNoteActivity::class.java) { it } // starts AddEditNoteActivity
        }

        // Get list of notes from database and set it to adapter's async list differ.
        lifecycleScope.launch {
            viewModel.notes.collect {
                adapter.differ.submitList(it) // submits list
            }
        }
    }


    override fun onNoteItemClick(note: Note) {
        // starts AddEditNoteActivity.
        startSpecificActivity(AddEditNoteActivity::class.java) {
            it.putExtra("note", note) // puts extra "note" value to intent
            it
        }
    }
}
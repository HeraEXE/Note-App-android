package com.example.notev.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notev.R
import com.example.notev.adapter.NotesListAdapter
import com.example.notev.data.models.Note
import com.example.notev.databinding.ActivityNotesListBinding
import com.example.notev.utils.Sorting
import com.example.notev.utils.extension.onQueryTextListener
import com.example.notev.utils.extension.saveSortingChanges
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
class NotesListActivity : AppCompatActivity() {

    // View Model.
    private val viewModel: NotesListViewModel by viewModels()

    // Shared Preferences.
    private lateinit var sharedPrefs: SharedPreferences

    // Adapter.
    private lateinit var adapter: NotesListAdapter

    // Binding.
    private lateinit var binding: ActivityNotesListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set shared preferences.
        sharedPrefs = getPreferences(MODE_PRIVATE)
        viewModel.setSorting(sharedPrefs.getInt("sorting", 1)) // sets sorting value (1 - Sort BY_OLDEST) by default.

        // Set binding.
        binding = ActivityNotesListBinding.inflate(layoutInflater)

        // Set layout.
        setContentView(binding.root)

        // Set title to action bar.
        supportActionBar?.title = getString(R.string.notes_list_activity_titles)

        // Set adapter.
        adapter = NotesListAdapter(context = this) { note ->
            // starts AddEditNoteActivity.
            startSpecificActivity(AddEditNoteActivity::class.java) {
                it.putExtra("note", note) // puts extra "note" value to intent
                it
            }
        }

        // Init Recycler View.
        binding.rvNotesList.let {
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notes_list, menu) // inflate menu

        // Set Search item.
        val searchItem = menu?.findItem(R.id.action_search) // find search menu item

        // Set SearchView.
        val searchView = searchItem?.actionView as SearchView
        searchView.run {
            queryHint = this@NotesListActivity.getText(R.string.search_hint) // set query hint for search view
            // Set query text listener.
            onQueryTextListener(false) {
                viewModel.setQuery(it) // sets new value to viewModel.query
            }
        }

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_sort_by_newest -> {
            viewModel.setSorting(Sorting.BY_NEWEST) // sorting by newest
            sharedPrefs.saveSortingChanges(Sorting.BY_NEWEST.id) // save sorting changes in shared preferences
            true // return
        }
        R.id.action_sort_by_oldest -> {
            viewModel.setSorting(Sorting.BY_OLDEST) // sorting by oldest
            sharedPrefs.saveSortingChanges(Sorting.BY_OLDEST.id) // save sorting changes in shared preferences
            true // return
        }
        R.id.action_sort_by_low -> {
            viewModel.setSorting(Sorting.BY_LOW_LEVEL) // sorting by low level
            sharedPrefs.saveSortingChanges(Sorting.BY_LOW_LEVEL.id) // save sorting changes in shared preferences
            true // return
        }
        R.id.action_sort_by_high -> {
            viewModel.setSorting(Sorting.BY_HIGH_LEVEL) // sorting by high level
            sharedPrefs.saveSortingChanges(Sorting.BY_HIGH_LEVEL.id) // save sorting changes in shared preferences
            true // return
        }
        else -> super.onOptionsItemSelected(item)
    }
}
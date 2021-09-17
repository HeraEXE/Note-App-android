package com.example.notev.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.notev.R
import com.example.notev.data.models.Note
import com.example.notev.databinding.ActivityAddEditNoteBinding
import com.example.notev.utils.NoteStatus
import com.example.notev.viewmodels.AddEditNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Editor for creating new or correcting old note.
 */
@AndroidEntryPoint
class AddEditNoteActivity : AppCompatActivity() {

    // View Model.
    private val viewModel: AddEditNoteViewModel by viewModels()

    // Binding.
    private lateinit var binding: ActivityAddEditNoteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set Binding
        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)

        // Set layout.
        setContentView(binding.root)

        // Gets note value from the intent.
        val note = intent.getParcelableExtra<Note>("note")
        if (note != null) { // checks whether note is not null
            viewModel.noteStatus = NoteStatus.EDIT // if yes, this is EDIT status.
            // Set note value to views.
            binding.run {
                etTitle.setText(note.title) // set title to edit text
                etContent.setText(note.content) // set content to edit text
                spinnerPriority.setSelection(note.priorityLevel) // set priority to spinner
            }
        } else {
            viewModel.noteStatus = NoteStatus.ADD // otherwise, this is ADD status.
        }
    }
}
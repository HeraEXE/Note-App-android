package com.example.notev.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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

        // Set display UP button.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Gets note value from the intent.
        val note = intent.getParcelableExtra<Note>("note")
        if (note != null) { // checks whether note is not null
            viewModel.noteStatus = NoteStatus.EDIT // if yes, this is EDIT status
            supportActionBar?.title =
                getString(R.string.edit_note_activity_title) // set title to action bar

            // Set note object to viewModel liveData.
            viewModel.setNote(note)

            // Set note value to views.
            binding.run {
                viewModel.note.value?.run {
                    etTitle.setText(title) // set title to edit text
                    etContent.setText(content) // set content to edit text
                    spinnerPriority.setSelection(priorityLevel) // set priority to spinner
                }
            }
        } else {
            viewModel.noteStatus = NoteStatus.ADD // otherwise, this is ADD status.
            supportActionBar?.title =
                getString(R.string.add_note_activity_title) // set title to action bar
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate menu by status.
        when (viewModel.noteStatus) {
            NoteStatus.ADD -> {
                menuInflater.inflate(R.menu.menu_add_note, menu) // status add - menu add
            }
            NoteStatus.EDIT -> {
                menuInflater.inflate(R.menu.menu_edit_note, menu) // status edit - menu edit
            }
        }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add -> { // ADD
            binding.run {
                val title = etTitle.text.toString() // get value from etTitle
                val content = etContent.text.toString() // get value from etContent

                // Validate.
                if (validate(title, content)) {
                    val priorityLevel = spinnerPriority.selectedItemId.toInt() // get priority level
                    val note = Note(title, content, priorityLevel) // create note object
                    viewModel.insert(note) // save note in the database
                    finish() // finish this activity
                }
            }
            true // returns
        }
        R.id.action_edit -> { // EDIT
            binding.run {
                val title = etTitle.text.toString() // get value from etTitle
                val content = etContent.text.toString() // get value from etContent

                // Validate.
                if (validate(title, content)) {

                    // Create and Show Alert Dialog.
                    AlertDialog
                        .Builder(this@AddEditNoteActivity)
                        .setCancelable(false) // user can't ignore it
                        .setTitle(this@AddEditNoteActivity.getText(R.string.dialog_edit_note_title)) // dialog title
                        .setMessage(this@AddEditNoteActivity.getText(R.string.dialog_edit_note_message)) // dialog message
                        // On Positive.
                        .setPositiveButton(this@AddEditNoteActivity.getText(R.string.dialog_edit_note_positive)) { dialog, _ ->
                            val priorityLevel =
                                spinnerPriority.selectedItemId.toInt() // get priority level
                            viewModel.note.value?.run {
                                viewModel.update(
                                    copy(
                                        title,
                                        content,
                                        priorityLevel
                                    )
                                ) // update edited note in the database
                            }
                            finish() // finish this activity
                            dialog.dismiss() // remove dialog from the display
                        }
                        // On Negative.
                        .setNegativeButton(this@AddEditNoteActivity.getText(R.string.dialog_cancel)) { dialog, _ ->
                            dialog.dismiss() // remove dialog from the display
                        }
                        .create() // create dialog
                        .show() // show dialog
                }
            }
            true // returns
        }
        R.id.action_delete -> { // DELETE

            // Create and Show Alert Dialog.
            AlertDialog
                .Builder(this@AddEditNoteActivity)
                .setCancelable(false) // user can't ignore it
                .setTitle(this@AddEditNoteActivity.getText(R.string.dialog_delete_note_title)) // dialog title
                .setMessage(this@AddEditNoteActivity.getText(R.string.dialog_delete_note_message)) // dialog message
                // On Positive.
                .setPositiveButton(this@AddEditNoteActivity.getText(R.string.dialog_delete_note_positive)) { dialog, _ ->
                    binding.run {
                        viewModel.note.value?.run {
                            viewModel.delete(this) // delete note in the database
                        }
                        finish() // finish this activity
                        dialog.dismiss() // remove dialog from the display
                    }
                }
                // On Negative.
                .setNegativeButton(this@AddEditNoteActivity.getText(R.string.dialog_cancel)) { dialog, _ ->
                    dialog.dismiss() // remove dialog from the display
                }
                .create() // create dialog
                .show() // show dialog
            true // returns
        }
        else -> super.onOptionsItemSelected(item)
    }


    /**
     * Validate user input edit text views.
     * @param [title] and [content] are user input.
     * @return [true] if is valid, [false] otherwise.
     */
    private fun validate(title: String, content: String): Boolean {
        var isValid = true // isValid flag

        // Checks whether title is empty.
        if (title.isEmpty()) {
            isValid = false // result is not valid
            binding.etTitle.error =
                this.getText(R.string.error_empty_text) // edit text will show error
        } else {
            binding.etTitle.error = null // edit text hides error
        }

        // Checks whether content is empty.
        if (content.isEmpty()) {
            isValid = false // result is not valid
            binding.etContent.error =
                this.getText(R.string.error_empty_text) // edit text will show error
        } else {
            binding.etContent.error = null // edit text hides error
        }
        return isValid // result
    }
}
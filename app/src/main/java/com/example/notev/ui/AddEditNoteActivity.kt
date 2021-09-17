package com.example.notev.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notev.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Editor for creating new or correcting old note.
 */
@AndroidEntryPoint
class AddEditNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set layout.
        setContentView(R.layout.activity_add_edit_note)
    }
}
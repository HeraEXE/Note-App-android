package com.example.notev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Editor for creating new or correcting old note.
 */
class AddEditNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set layout.
        setContentView(R.layout.activity_add_edit_note)
    }
}
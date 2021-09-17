package com.example.notev.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notev.R

/**
 * The start point (launcher) [Activity].
 * Shows user's list of notes.
 */
class NotesListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set layout.
        setContentView(R.layout.activity_notes_list)
    }
}
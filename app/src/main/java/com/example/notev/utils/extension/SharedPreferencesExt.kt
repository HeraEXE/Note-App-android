package com.example.notev.utils.extension

import android.content.SharedPreferences

/*
This file holds all custom SharedPreferences extensions.
 */


/**
 * Saves sorting id in the shared preferences.
 */
fun SharedPreferences.saveSortingChanges(sortingId: Int) {
    this.edit().run {
        putInt("sorting", sortingId) // puts id value
        apply() // applies changes
    }
}
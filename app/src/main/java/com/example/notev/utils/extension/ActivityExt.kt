package com.example.notev.utils.extension

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast

/*
This file holds all custom Activity Extensions.
 */

/**
 * Shows a toast.
 * @param used [text] to specify a message and [duration] to specify how long to show a Toast.
 * @return [Unit].
 */
fun Activity.showToast(text: String?, duration: Int) {
    Toast.makeText(this, text, duration).show() // makes and show the toast.
}


/**
 * Starts specified [Activity].
 * @param used [activity] which user want to start and [updateIntent] lambda to specify intent.
 * @return [true] if activity was started successfully, or [false] if [ActivityNotFoundException] was catched.
 */
fun Activity.startSpecificActivity(activity: Class<*>, updateIntent: (Intent) -> Intent): Boolean {
    // Create intent.
    val intent = updateIntent(Intent(this, activity))

    // Starts specified activity and returns result.
    return try {
        startActivity(intent)
        true // activity was started successfully
    } catch (e: ActivityNotFoundException) {
        false // something got wrong
    }
}
package com.example.notev.utils

/**
 * Sorting enum class, for sorting database result.
 */
enum class Sorting(val id: Int) {
    BY_NEWEST(0),
    BY_OLDEST(1),
    BY_LOW_LEVEL(2),
    BY_HIGH_LEVEL(3)
}
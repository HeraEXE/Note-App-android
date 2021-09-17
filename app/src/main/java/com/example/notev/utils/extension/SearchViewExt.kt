package com.example.notev.utils.extension

import androidx.appcompat.widget.SearchView

/*
This file holds all custom SearchView extensions.
 */

/**
 * Sets [SearchView] onQueryTextListener.
 * @param uses  [isOnSubmit] true - search on Submit, otherwise - search on Change. implement [doSearch] to add search logic.
 */
fun SearchView.onQueryTextListener(isOnSubmit: Boolean, doSearch: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener{

        override fun onQueryTextSubmit(query: String?): Boolean {
            if (!isOnSubmit) return false // if isOnSubmit false - do Search on Change
            if (query != null) doSearch(query) // if query is not null call doSearch()
            return true
        }


        override fun onQueryTextChange(newText: String?): Boolean {
            if (isOnSubmit) return false // if isOnSubmit true - do Search on Submit
            if (newText != null) doSearch(newText) // if newText is not null call doSearch()
            return true
        }
    })
}
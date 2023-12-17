package com.example.afisha.common.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Used to set the title for a fragment.
 */
fun Fragment.setTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar?.show()
    title.let {
        (activity as AppCompatActivity).supportActionBar?.title = it
    }
}

/**
 * Used to set the visibility of the "Up" button.
 */
fun Fragment.setDisplayHomeAsUpEnabled(enabled: Boolean) {
    val actionBar = (activity as? AppCompatActivity)?.supportActionBar
    actionBar?.setDisplayHomeAsUpEnabled(enabled)
}

/**
 * Used to subscribe to [Flow] with components for filtering out identical values.
 */
suspend fun Flow<CombinedLoadStates>.collectDistinct(block: (LoadState) -> Unit) {
    map { it.refresh }.distinctUntilChanged().collect(block)
}
package com.example.afisha.ui.top.uiEvent

import com.example.afisha.domain.model.Movie

/**
 * UI events for the fragment [MoviewTopFragment]
 */
sealed interface MovieUiEvent {

    /**
     * Open the [MovieDetailFragment] fragment with the movie as the [movie] parameter
     */
    data class OpenMovieDetailFragment(val movie: Movie) : MovieUiEvent
}
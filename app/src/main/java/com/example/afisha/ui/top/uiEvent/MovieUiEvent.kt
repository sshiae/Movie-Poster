package com.example.afisha.ui.top.uiEvent

import com.example.afisha.domain.model.Movie

/**
 * UI события для фрагмента [MoviewTopFragment]
 */
sealed interface MovieUiEvent {

    /**
     * Открыть фрагмент [MovieDetailFragment] с фильмом в виде параметра [movie]
     */
    data class OpenMovieDetailFragment(val movie: Movie) : MovieUiEvent
}
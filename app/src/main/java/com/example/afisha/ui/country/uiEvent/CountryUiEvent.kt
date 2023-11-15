package com.example.afisha.ui.country.uiEvent

import com.example.afisha.domain.model.Country

/**
 * UI события для фрагмента [CountryFragment]
 */
sealed interface CountryUiEvent {

    /**
     * Открыть фрагмент с топом фильмов [MovieTopFragment]
     */
    data class OpenMovieTopScreen(val country: Country) : CountryUiEvent
}
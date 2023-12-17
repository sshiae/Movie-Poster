package com.example.afisha.ui.country.uiEvent

import com.example.afisha.domain.model.Country

/**
 * UI events for the [CountryFragment].
 */
sealed interface CountryUiEvent {

    /**
     * Open the fragment with the top movies [MovieTopFragment].
     */
    data class OpenMovieTopScreen(val country: Country) : CountryUiEvent
}
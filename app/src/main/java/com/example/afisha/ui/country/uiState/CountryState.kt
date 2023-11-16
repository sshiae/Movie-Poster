package com.example.afisha.ui.country.uiState

import com.example.afisha.base.ui.BaseUiState
import com.example.afisha.domain.model.Country

/**
 * UI состояние для страны [Country]
 */
data class CountryState(
    val id: Long,
    val title: String,
    override val original: Country
) : BaseUiState<Country>()
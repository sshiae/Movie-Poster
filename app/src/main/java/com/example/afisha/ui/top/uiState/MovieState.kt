package com.example.afisha.ui.top.uiState

import androidx.annotation.ColorInt
import com.example.afisha.base.ui.BaseUiState
import com.example.afisha.domain.model.Movie

/**
 * Состояние фильма [Movie] для списка
 */
data class MovieState(
    val title: String,
    val posterUrl: String,
    val genres: String,
    val description: String,
    val rating: String,
    @ColorInt val colorRating: Int,
    override val original: Movie
) : BaseUiState<Movie>()
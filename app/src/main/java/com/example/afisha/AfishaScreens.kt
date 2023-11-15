package com.example.afisha

import android.content.Intent
import android.net.Uri
import com.example.afisha.domain.model.Country
import com.example.afisha.domain.model.Movie
import com.example.afisha.ui.country.CountryFragment
import com.example.afisha.ui.movieDetail.MovieDetailFragment
import com.example.afisha.ui.top.MovieTopFragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

/**
 * Основные экраны приложения
 */
object AfishaScreens {

    /**
     * Экран для выбора страны
     */
    fun CountryScreen() = FragmentScreen {
        CountryFragment()
    }

    /**
     * Экран для просмотра топа фильмов в выбранной стране [country]
     */
    fun MovieTopScreen(country: Country) = FragmentScreen {
        MovieTopFragment(country)
    }

    /**
     * Экран для просмотра карточки фильма с выбранным фильмом [movie]
     */
    fun MovieDetailScreen(movie: Movie) = FragmentScreen {
        MovieDetailFragment(movie)
    }

    /**
     * Внешний экран, на переход который осуществуляется по ссылке [url]
     */
    fun ExternalScreen(url: String) = ActivityScreen {
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }
}
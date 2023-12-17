package com.example.afisha

import android.content.Intent
import android.net.Uri
import com.example.afisha.domain.model.Country
import com.example.afisha.ui.country.CountryFragment
import com.example.afisha.ui.movieDetail.MovieDetailFragment
import com.example.afisha.ui.top.MovieTopFragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

/**
 * Main screens of the application.
 */
object AfishaScreens {

    /**
     * Screen for selecting a country.
     */
    fun CountryScreen() = FragmentScreen {
        CountryFragment()
    }

    /**
     * Screen for viewing the top movies in the selected country [country].
     */
    fun MovieTopScreen(country: Country) = FragmentScreen {
        MovieTopFragment.getNewInstance(country.name)
    }

    /**
     * Screen for viewing the details of a movie with the selected movie [name] and [id].
     */
    fun MovieDetailScreen(name: String, id: Int) = FragmentScreen {
        MovieDetailFragment.getNewInstance(name, id)
    }

    /**
     * External screen that is navigated to via the link [url].
     */
    fun ExternalScreen(url: String) = ActivityScreen {
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }
}
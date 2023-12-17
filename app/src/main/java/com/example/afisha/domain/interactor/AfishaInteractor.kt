package com.example.afisha.domain.interactor

import androidx.paging.PagingData
import com.example.afisha.data.local.AfishaLocalRepository
import com.example.afisha.data.remote.AfishaRemoteRepository
import com.example.afisha.domain.model.Country
import com.example.afisha.domain.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Interactor for interacting with various repositories.
 */
class AfishaInteractor @Inject constructor(
    private val localRepository: AfishaLocalRepository,
    private val remoteRepository: AfishaRemoteRepository
) {
    suspend fun existsCountries(): Boolean {
        return localRepository.existsCountries()
    }

    suspend fun insertCountries(jsonData: String) {
        val gson = Gson()
        val type = object : TypeToken<List<Country>>() {}.type
        val countries: List<Country> = gson.fromJson(jsonData, type)
        localRepository.insertCountries(countries)
    }

    suspend fun getAllCountries(searchString: String): Flow<PagingData<Country>> {
        return localRepository.getCountries(searchString)
    }

    suspend fun getMoviesSortedByRating(country: String): Flow<PagingData<Movie>> {
        return remoteRepository.getMoviesSortedByRating(country)
    }

    suspend fun getMovieById(id: Int): Movie {
        return remoteRepository.getMovieById(id)
    }
}
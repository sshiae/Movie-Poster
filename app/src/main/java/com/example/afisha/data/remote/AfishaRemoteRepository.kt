package com.example.afisha.data.remote

import androidx.paging.PagingData
import com.example.afisha.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Remote repository for network operations.
 */
interface AfishaRemoteRepository {

    /**
     * Used to retrieve a list of movies [Movie] sorted by rating.
     *
     * @param country - country
     */
    suspend fun getMoviesSortedByRating(country: String): Flow<PagingData<Movie>>

    /**
     * Used to retrieve a movie by [id].
     *
     * @param id - movie ID
     */
    suspend fun getMovieById(id: Int): Movie
}